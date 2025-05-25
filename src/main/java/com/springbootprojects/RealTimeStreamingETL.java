package com.springbootprojects;

import com.springbootprojects.deserializers.IotDeserializer;
import com.springbootprojects.models.IotEvent;
import com.springbootprojects.options.CustomOptions;
import com.springbootprojects.options.OptionsLoader;
import com.springbootprojects.transforms.EventsFilter;
import com.springbootprojects.transforms.IOEventsPrinter;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.jdbc.JdbcIO;
import org.apache.beam.sdk.io.kafka.KafkaIO;
import org.apache.beam.sdk.transforms.Count;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.transforms.Values;
import org.apache.beam.sdk.transforms.windowing.FixedWindows;
import org.apache.beam.sdk.transforms.windowing.Window;
import org.apache.beam.sdk.values.KV;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.joda.time.Duration;

public class RealTimeStreamingETL {
    public static void main(String[] args) {
        CustomOptions options = OptionsLoader.loadOptions();
        Pipeline p = Pipeline.create();

        p.apply("Consume IoT events", KafkaIO.<Long, IotEvent>read()
                        .withBootstrapServers(options.getKafkaBootstrapServers())
                        .withTopic(options.getKafkaTopic())
                        .withKeyDeserializer(LongDeserializer.class)
                        .withValueDeserializer(IotDeserializer.class)
                        .withoutMetadata())
                .apply(Values.create())
                .apply(ParDo.of(new IOEventsPrinter()))
                .apply(Window.<IotEvent>into(FixedWindows.of(Duration.standardSeconds(5))))
                .apply("Filter events", ParDo.of(new EventsFilter()))
                .apply(Count.perElement())
                .apply("Persist events", JdbcIO.<KV<String, Long>>write()
                        .withDataSourceConfiguration(JdbcIO.DataSourceConfiguration.create(
                                                options.getDbDriverClass(),
                                                options.getJdbcUrl()
                                        )
                                        .withUsername(options.getDbUserName())
                                        .withPassword(options.getDbPassword())
                        ).withStatement("INSERT INTO event(device_id, events_count)" +
                                "VALUES (?, ?)" +
                                "ON CONFLICT (device_id)" +
                                "DO UPDATE SET events_count = event.events_count + EXCLUDED.events_count")
                        .withPreparedStatementSetter(((element, preparedStatement) -> {
                            preparedStatement.setString(1, element.getKey());
                            preparedStatement.setLong(2, element.getValue());
                        })));

        p.run();
    }
}

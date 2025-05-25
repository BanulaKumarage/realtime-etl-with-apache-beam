package com.springbootprojects.deserializers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springbootprojects.models.IotEvent;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;
import java.util.Map;

public class IotDeserializer implements Deserializer<IotEvent> {
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        Deserializer.super.configure(configs, isKey);
    }

    @Override
    public IotEvent deserialize(String s, byte[] bytes) {
        ObjectMapper mapper = new ObjectMapper();

        IotEvent iotEvent = null;
        try {
            iotEvent = mapper.readValue(bytes, IotEvent.class);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return iotEvent;
    }

    @Override
    public IotEvent deserialize(String topic, Headers headers, byte[] data) {
        return Deserializer.super.deserialize(topic, headers, data);
    }

    @Override
    public void close() {
        Deserializer.super.close();
    }
}

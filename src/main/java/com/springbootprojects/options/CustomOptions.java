package com.springbootprojects.options;

import org.apache.beam.sdk.options.PipelineOptions;

public interface CustomOptions extends PipelineOptions {
    void setKafkaBootstrapServers(String bootstrapServers);
    String getKafkaBootstrapServers();

    void setKafkaTopic(String topic);
    String getKafkaTopic();

    void setDbDriverClass(String dbDriverClass);
    String getDbDriverClass();

    void setJdbcUrl(String jdbcUrl);
    String getJdbcUrl();

    void setDbUserName(String dbUser);
    String getDbUserName();

    void setDbPassword(String dbPassword);
    String getDbPassword();
}

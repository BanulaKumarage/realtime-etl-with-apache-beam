package com.springbootprojects.options;

import org.apache.beam.sdk.options.PipelineOptionsFactory;

import java.io.InputStream;
import java.util.Properties;

public class OptionsLoader {

    public static CustomOptions loadOptions() {
        Properties properties = new Properties();
        try (InputStream input = OptionsLoader.class.getClassLoader()
                .getResourceAsStream("application.properties")) {
            if (input == null) {
                throw new RuntimeException("application.properties not found");
            }
            properties.load(input);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load application.properties", e);
        }

        String[] args = properties.entrySet().stream()
                .map(e -> "--" + e.getKey() + "=" + e.getValue()).toArray(String[]::new);

        return PipelineOptionsFactory.fromArgs(args)
                .withValidation()
                .as(CustomOptions.class);
    }
}

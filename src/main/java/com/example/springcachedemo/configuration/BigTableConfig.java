package com.example.springcachedemo.configuration;

import com.google.cloud.ServiceOptions;
import com.google.cloud.bigtable.data.v2.BigtableDataClient;
import com.google.cloud.bigtable.data.v2.BigtableDataSettings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
@ConditionalOnProperty(value = "storage.mode", havingValue = "bigtable")
public class BigTableConfig {

    @Value("${gcp.bigtable.instanceId}")
    private String instanceId;

    @Bean
    public BigtableDataClient bigtableDataSettings() throws IOException {
        var settings = BigtableDataSettings.newBuilder()
                .setProjectId(ServiceOptions.getDefaultProjectId())
                .setInstanceId(instanceId)
                .build();
        return BigtableDataClient.create(settings);
    }
}

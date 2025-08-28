package com.varshith.filemetadata;

import io.dapr.client.DaprClient;
import io.dapr.client.DaprClientBuilder;
import io.dapr.config.Properties;
import io.dapr.testcontainers.DaprContainer;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.utility.MountableFile;

import java.time.Duration;
import java.util.Collections;

@TestConfiguration(proxyBeanMethods = false)
public class DaprTestContainersConfig {

    @Bean
    DaprClient daprClient(DaprContainer container) {
        return new DaprClientBuilder()
                .withPropertyOverride(Properties.GRPC_PORT, container.getMappedPort(Properties.GRPC_PORT.get()).toString())
                .build();
    }

    @Bean
    @ServiceConnection
    public DaprContainer daprContainer() {
        return new DaprContainer("daprio/daprd:1.14.4")
                .withAppName("filemetadata")
                .withAppPort(8080)
                .withCopyFileToContainer(MountableFile.forClasspathResource("components"), "/components/")
                .withStartupTimeout(Duration.ofSeconds(180));
    }
}
package com.varshith.filemetadata.service;

import org.springframework.stereotype.Service;
import io.dapr.client.DaprClient;

import reactor.core.publisher.Mono;
import java.util.HashMap;
import java.util.Map;

@Service
public class AzureBlobStorageService {

    public static final byte[] BYTES = new byte[0];
    private final DaprClient daprClient;

    public AzureBlobStorageService(DaprClient daprClient) {
        this.daprClient = daprClient;
    }

    public Mono<byte[]> downloadFile(String fileName) {
        Map<String, String> metadata = new HashMap<>();
        metadata.put("blobName", fileName);

        return this.daprClient.invokeBinding("azureblob", "get", BYTES, metadata);
    }
}
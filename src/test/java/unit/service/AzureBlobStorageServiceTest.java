package unit.service;

import io.dapr.client.DaprClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import com.varshith.filemetadata.service.AzureBlobStorageService;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class AzureBlobStorageServiceTest {

    private DaprClient daprClient;
    private AzureBlobStorageService service;

    @BeforeEach
    void setUp() {
        daprClient = Mockito.mock(DaprClient.class);
        service = new AzureBlobStorageService(daprClient);
    }

    @Test
    void downloadFile_ReturnsData_WhenDaprReturnsData() {
        String fileName = "test.txt";
        byte[] expectedBytes = "hello world".getBytes();

        Map<String, String> expectedMetadata = Map.of("blobName", fileName);
        when(daprClient.invokeBinding(
                eq("azureblob"),
                eq("get"),
                eq(AzureBlobStorageService.BYTES),
                eq(expectedMetadata)
        )).thenReturn(Mono.just(expectedBytes));

        StepVerifier.create(service.downloadFile(fileName))
                .expectNext(expectedBytes)
                .verifyComplete();
    }

    @Test
    void downloadFile_ReturnsEmpty_WhenDaprReturnsEmpty() {
        String fileName = "empty.txt";
        Map<String, String> expectedMetadata = Map.of("blobName", fileName);

        when(daprClient.invokeBinding(
                eq("azureblob"),
                eq("get"),
                eq(AzureBlobStorageService.BYTES),
                eq(expectedMetadata)
        )).thenReturn(Mono.just(new byte[0]));

        StepVerifier.create(service.downloadFile(fileName))
                .assertNext(actual -> assertArrayEquals(new byte[0], actual))
                .verifyComplete();
    }
}
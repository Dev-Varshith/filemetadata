package unit.controller;

import com.varshith.filemetadata.controller.MetaDataController;
import com.varshith.filemetadata.service.AzureBlobStorageService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class MetaDataControllerTest {

    @Test
    void downloadFile_ReturnsBytes() {
        AzureBlobStorageService service = Mockito.mock(AzureBlobStorageService.class);
        MetaDataController controller = new MetaDataController(service);

        String fileName = "test.txt";
        byte[] expectedBytes = "hello world".getBytes();

        Mockito.when(service.downloadFile(fileName)).thenReturn(Mono.just(expectedBytes));

        StepVerifier.create(controller.downloadFile(fileName))
                .expectNext(expectedBytes)
                .verifyComplete();

        Mockito.verify(service).downloadFile(fileName);
    }
}
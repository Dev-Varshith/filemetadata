package intg.controller;

import com.azure.storage.blob.*;
import com.azure.storage.blob.models.*;
import com.varshith.filemetadata.FilemetadataApplication;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.web.reactive.server.WebTestClient;


import java.nio.charset.StandardCharsets;

@SpringBootTest(classes = FilemetadataApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MetaDataControllerTest {

    static final String CONTAINER_NAME = "testcontainer";
    static final String BLOB_NAME = "test.txt";
    static final String FILE_CONTENT = "Hi, Azure Blob!";

    @Autowired
    WebTestClient webTestClient;

    @Test
    void testDownloadFileFromAPI() {
        webTestClient.get()
                .uri("/api/files/download/" + BLOB_NAME)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(byte[].class)
                .isEqualTo(FILE_CONTENT.getBytes(StandardCharsets.UTF_8));
    }
}
package com.varshith.filemetadata.controller;

import com.varshith.filemetadata.service.AzureBlobStorageService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/files")
public class MetaDataController {

    private final AzureBlobStorageService azureBlobStorageService;

    public MetaDataController(AzureBlobStorageService azureBlobStorageService) {
        this.azureBlobStorageService = azureBlobStorageService;
    }

    @GetMapping("/download/{fileName}")
    public Mono<byte[]> downloadFile(@PathVariable String fileName) {
        return azureBlobStorageService.downloadFile(fileName);
    }
}

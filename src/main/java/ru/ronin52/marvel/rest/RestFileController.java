package ru.ronin52.marvel.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.ronin52.marvel.domain.UploadInfo;
import ru.ronin52.marvel.dto.UploadResponseDto;
import ru.ronin52.marvel.service.FileService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/file")
public class RestFileController {
    private final FileService service;

    @PostMapping("/upload")
    public UploadResponseDto upload(@RequestParam MultipartFile file) {
        return service.save(file);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resource> get(@PathVariable String id) {
        UploadInfo uploadInfo = service.get(id);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(uploadInfo.getMimeType()))
                .body(uploadInfo.getResource());
    }
}

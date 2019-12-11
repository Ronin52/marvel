package ru.ronin52.marvel.service;

import org.springframework.web.multipart.MultipartFile;
import ru.ronin52.marvel.domain.UploadInfo;
import ru.ronin52.marvel.dto.UploadResponseDto;

public interface FileService {
    UploadResponseDto save(MultipartFile multipartFile);
    UploadInfo get(String id);
}

package ru.ronin52.marvel.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.ronin52.marvel.domain.UploadInfo;
import ru.ronin52.marvel.dto.UploadResponseDto;
import ru.ronin52.marvel.exception.FileStorageException;
import ru.ronin52.marvel.exception.UnsupportedFileTypeException;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    private final static Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);
    private final String uploadPath;

    public FileServiceImpl(@Value("${app.upload-path}") String uploadPath) {
        logger.info(uploadPath);
        this.uploadPath = uploadPath;
    }

    @PostConstruct
    public void init() throws IOException {
        Path path = Paths.get(uploadPath);
        if (Files.notExists(path)) {
            Files.createDirectories(path);
        }
    }

    public UploadResponseDto save(MultipartFile multipartFile) {
        String extension;
        String contentType = multipartFile.getContentType();
        if ("image/jpeg".equals(contentType)) {
            extension = ".jpg";
        } else if ("image/png".equals(contentType)) {
            extension = ".png";
        } else {
            throw new UnsupportedFileTypeException(contentType);
        }

        String name = UUID.randomUUID() + extension;
        try {
            multipartFile.transferTo(Paths.get(uploadPath).resolve(name));
            return new UploadResponseDto(name);
        } catch (IOException e) {
            throw new FileStorageException(e);
        }
    }

    public UploadInfo get(String id) {
        try {
            return new UploadInfo(
                    new FileSystemResource(Paths.get(uploadPath).resolve(id)),
                    Files.probeContentType(Paths.get(uploadPath).resolve(id))
            );
        } catch (IOException e) {
            throw new FileStorageException(e);
        }
    }

}

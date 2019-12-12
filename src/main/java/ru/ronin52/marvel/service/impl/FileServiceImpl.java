package ru.ronin52.marvel.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.ronin52.marvel.domain.UploadInfo;
import ru.ronin52.marvel.dto.UploadResponseDto;
import ru.ronin52.marvel.exception.ContentTypeIsNullException;
import ru.ronin52.marvel.exception.FileStorageException;
import ru.ronin52.marvel.exception.UnsupportedFileTypeException;
import ru.ronin52.marvel.service.FileService;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    private final String uploadPath;

    private enum FILE_CONTENT_TYPE {
        JPEG("image/jpeg"),
        PNG("image/png");

        private String name;

        FILE_CONTENT_TYPE(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public static FILE_CONTENT_TYPE getType(String typeName) {
            for (FILE_CONTENT_TYPE value : FILE_CONTENT_TYPE.values()) {
                if(value.getName().equals(typeName)){
                    return value;
                }
            }
            throw new UnsupportedFileTypeException(typeName);
        }
    }

    private static final String EXTENSION_JPEG = ".jpg";
    private static final String EXTENSION_PNG = ".png";


    public FileServiceImpl(@Value("${app.upload-path}") String uploadPath) {
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
        final String name;
        String contentType = multipartFile.getContentType();
        if (contentType == null) {
            throw new ContentTypeIsNullException();
        }
        switch (FILE_CONTENT_TYPE.getType(contentType)) {
            case JPEG:
                name = UUID.randomUUID() + EXTENSION_JPEG;
                break;
            case PNG:
                name = UUID.randomUUID() + EXTENSION_PNG;
                break;
            default:
                throw new UnsupportedFileTypeException(contentType);
        }
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

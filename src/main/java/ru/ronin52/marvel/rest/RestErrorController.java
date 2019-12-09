package ru.ronin52.marvel.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import ru.ronin52.marvel.dto.ErrorResponseDto;
import ru.ronin52.marvel.exception.FileStorageException;
import ru.ronin52.marvel.exception.UnsupportedFileTypeException;

import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;

@RestController
@RequestMapping("${server.error.path:${error.path:/error}}")
public class RestErrorController extends AbstractErrorController {
    private final ErrorAttributes errorAttributes;
    private final String path;

    public RestErrorController(ErrorAttributes errorAttributes, @Value("${server.error.path:${error.path:/error}}") String path) {
        super(errorAttributes);
        this.errorAttributes = errorAttributes;
        this.path = path;
    }

    @RequestMapping
    public ResponseEntity<ErrorResponseDto> error(HttpServletRequest request) {
        ServletWebRequest webRequest = new ServletWebRequest(request);
        Throwable error = errorAttributes.getError(webRequest);
        int status = getStatus(request).value();
        String message = "error.unknown";
        if (status == 404) {
            message = "error.not_found";
        }
        if (error == null) {
            return ResponseEntity.status(status).body(
                    new ErrorResponseDto(status, message)
            );
        }
        if (error instanceof FileNotFoundException) {
            status = 404;
            message = "error.file_not_found";
            return getErrorDto(error, status, message);
        }
        if (error instanceof FileStorageException) {
            status = 400;
            message = "error.can_not_save_file";
            return getErrorDto(error, status, message);
        }
        if (error instanceof UnsupportedFileTypeException) {
            status = 400;
            message = "error.bad_filetype";
            return getErrorDto(error, status, message);
        }
        return getErrorDto(error, status, message);
    }

    private ResponseEntity<ErrorResponseDto> getErrorDto(Throwable error, int status, String message) {
        error.printStackTrace();
        return ResponseEntity.status(status).body(
                new ErrorResponseDto(status, message)
        );
    }

    @Override
    public String getErrorPath() {
        return path;
    }
}
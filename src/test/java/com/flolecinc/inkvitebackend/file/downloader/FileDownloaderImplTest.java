package com.flolecinc.inkvitebackend.file.downloader;

import com.flolecinc.inkvitebackend.exceptions.InvalidDownloadURLException;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

class FileDownloaderImplTest {

    public static final byte[] MOCK_IMAGE_BYTES = new byte[]{1, 2, 3};
    public static final MockMultipartFile MOCK_MULTIPART_FILE = new MockMultipartFile("file", "image.jpg", "image/jpeg", MOCK_IMAGE_BYTES);

    private final FileDownloaderImpl fileDownloaderImpl = new FileDownloaderImpl();

    @Test
    void getBytesFromMultipartFile() throws IOException {
        byte[] result = fileDownloaderImpl.getBytesFromMultipartFile(MOCK_MULTIPART_FILE);
        assertNotNull(result);
        assertArrayEquals(MOCK_IMAGE_BYTES, result);
    }

    @Test
    void validateUrl_validUrl() {
        String validUrl = "https://example.com/image.jpg";
        URL url = fileDownloaderImpl.validateUrl(validUrl);
        assertNotNull(url);
        assertArrayEquals(validUrl.getBytes(), url.toString().getBytes());
    }

    @Test
    void validateUrl_invalidUrl() {
        String invalidUrl = "invalid-url";
        assertThrows(InvalidDownloadURLException.class, () -> fileDownloaderImpl.validateUrl(invalidUrl));
    }

    @Test
    void validateUrl_unauthorizedProtocol() {
        String unauthorizedUrl = "ftp://example.com/image.jpg";
        assertThrows(InvalidDownloadURLException.class, () -> fileDownloaderImpl.validateUrl(unauthorizedUrl));
    }

}
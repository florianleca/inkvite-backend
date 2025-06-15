package com.flolecinc.inkvitebackend.file.downloader;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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

}
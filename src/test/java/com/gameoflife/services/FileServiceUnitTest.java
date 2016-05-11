package com.gameoflife.services;

import com.gameoflife.services.FileService;
import org.junit.Test;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

public class FileServiceUnitTest {

    FileService sut = new FileService();

    @Test(expected = IllegalArgumentException.class)
    public void readFileWillThrowAnExceptionIfTheFilePathIsNull() {

        // test fixtures
        final String filePath = null;

        // when
        sut.readFile(filePath);
    }

    @Test
    public void readFileWillReturnTheFileRequested() {

        // test fixtures
        final String filePath = "inputFile.txt";

        // when
        File result = sut.readFile(filePath);

        // then
        assertThat(result).isNotNull();
    }

    @Test(expected = IllegalArgumentException.class)
    public void readFileWithInvalidFilenameWillThrowAnException() {

        // test fixtures
        final String filePath = "i-dont-exist.txt";

        // when
        sut.readFile(filePath);
    }
}
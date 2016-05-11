package com.gameoflife;

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
}
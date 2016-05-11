package com.gameoflife;

import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.io.File;

import static org.springframework.util.Assert.notNull;

@Component
public class FileService {

    public File readFile(@Nonnull String filePath) {

        notNull(filePath);

        ClassLoader classLoader = getClass().getClassLoader();
        return new File(classLoader.getResource(filePath).getFile());
    }
}

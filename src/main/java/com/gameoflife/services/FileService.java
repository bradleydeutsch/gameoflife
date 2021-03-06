package com.gameoflife.services;

import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.io.File;
import java.net.URL;

import static java.lang.String.format;
import static org.springframework.util.Assert.notNull;

@Component
public class FileService {

    @Nonnull
    public File readFile(@Nonnull final String filePath) {

        notNull(filePath);

        final ClassLoader classLoader = getClass().getClassLoader();
        final URL resource = classLoader.getResource(filePath);

        if (resource == null) {
            throw new IllegalArgumentException(format("No resource found for path \"%s\"", filePath));
        }

        return new File(resource.getFile());
    }
}

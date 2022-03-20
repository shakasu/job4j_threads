package ru.job4j.io;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class SaveFile {
    private final File file;
    
    public SaveFile(File file) {
        this.file = file;
    }
    
    public synchronized void saveContent(String content) {
        try (FileOutputStream out = new FileOutputStream(file);
             BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(out)) {
            bufferedOutputStream.write(
                content.getBytes(StandardCharsets.UTF_8),
                0, content.length()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

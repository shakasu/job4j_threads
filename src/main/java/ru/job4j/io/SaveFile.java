package ru.job4j.io;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class SaveFile {
    private final File file;
    
    public SaveFile(File file) {
        this.file = file;
    }
    
    public void saveContent(String content) throws IOException {
        OutputStream o = new FileOutputStream(file);
        for (int i = 0; i < content.length(); i += 1) {
            o.write(content.charAt(i));
        }
        
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

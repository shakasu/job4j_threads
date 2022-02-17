package ru.job4j.io;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.function.Predicate;

public class ParseFile {
    private final File file;
    
    public ParseFile(File file) {
        this.file = file;
    }
    
    public String getContent() throws IOException {
        return content(data -> true);
    }
    
    public String getContentWithoutUnicode() throws IOException {
        return content(data -> data < 0x80);
    }
    
    private String content(Predicate<Integer> filter) throws IOException {
        String output = "";
        int data;
        
        try (FileInputStream in = new FileInputStream(file);
             BufferedInputStream bufferedInStream = new BufferedInputStream(in)) {
            while ((data = bufferedInStream.read()) > 0) {
                if (filter.test(data)) {
                    output += (char) data;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return output;
    }
}

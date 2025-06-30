package api.v1.dao;

import api.v1.models.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Random;

@Component
public class FileWorker {

    public String readfile(){
        Path filePath = Paths.get("src/main/resources/reader.txt").toAbsolutePath();
        Random random = new Random();
        try(RandomAccessFile randomAccessFile = new RandomAccessFile(filePath.toFile(), "r")) {
            String line = null;
            while (line == null) {
                randomAccessFile.seek(random.nextLong(randomAccessFile.length()));
                randomAccessFile.readLine();
                line =  randomAccessFile.readLine();
            }
            return line;
        } catch (FileNotFoundException e) {
            return e.getMessage();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    public void writefile(User user){
        Path filePath = Paths.get("src/main/resources/writer.txt").toAbsolutePath();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String userJSON = objectMapper.writeValueAsString(user) + "\n";
            if (Files.exists(filePath)) {
                Files.writeString(filePath, userJSON, StandardCharsets.UTF_8, StandardOpenOption.APPEND);
            } else {
                Files.writeString(filePath, userJSON, StandardCharsets.UTF_8, StandardOpenOption.CREATE);
            }
        } catch (JsonProcessingException e) {
            System.out.println("Error with user JSON: " + e.getMessage());
            throw new RuntimeException(e.getMessage());
        } catch (IOException e) {
            System.out.println("Error with write to file: " + e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }
}

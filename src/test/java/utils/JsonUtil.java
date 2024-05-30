package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import jsonObjects.PetObject;

import java.io.IOException;
import java.io.InputStream;

public class JsonUtil {
    public static PetObject readJsonFromFile(String fileName, Class<PetObject> valueType) {
        ObjectMapper objectMapper = new ObjectMapper();

        try (InputStream inputStream = JsonUtil.class.getClassLoader().getResourceAsStream(fileName)) {
            if (inputStream == null) {
                throw new IllegalArgumentException("File not found: " + fileName);
            }
            PetObject pet = objectMapper.readValue(inputStream, valueType);
            return pet;
        } catch (IOException e) {
            throw new RuntimeException("Failed to read JSON file", e);
        }
    }
}

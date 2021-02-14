package pl.faustyna.gallery.translation.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {
    public static Object convertToJson(final String jsonString) {
        final ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(jsonString, Object.class);
        } catch (final JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static String convertToString(final Object json) {
        final ObjectMapper objectMapper = new ObjectMapper();

        try {
            return objectMapper.writeValueAsString(json);
        } catch (final JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }
    }


}

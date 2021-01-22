package ru.cortan122.georest;

import org.json.JSONException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class LocationRequesterTest {
    private final LocationRequester lr = new LocationRequester();

    @Test
    void makeHttpRequest() throws IOException {
        assertTrue(lr.makeHttpRequest("http://example.com").contains("Example Domain"));
        assertThrows(IOException.class, () -> lr.makeHttpRequest("http://does_not.exist"));
    }

    @Test
    void makeApiRequest() throws IOException {
        assertEquals(lr.makeApiRequest("https://jsonplaceholder.typicode.com/posts/1").getInt("id"), 1);
        assertThrows(JSONException.class, () -> lr.makeApiRequest("http://example.com"));
    }

    @Test
    void parseJson() {
        assertThrows(JSONException.class, () -> lr.parseJson("not json"));
        assertEquals(lr.parseJson("{\"key\": 122}").getInt("key"), 122);
    }

    @Test
    void getLocationJson() throws IOException {
        var res = lr.getLocationJson();
        assertDoesNotThrow(() -> res.getString("country_name"));
        assertDoesNotThrow(() -> res.getString("region_name"));
        assertDoesNotThrow(() -> res.getString("city"));
        assertDoesNotThrow(() -> res.getFloat("latitude"));
        assertDoesNotThrow(() -> res.getFloat("longitude"));
    }

    @Test
    void getLocationString() throws IOException {
        var res = lr.getLocationString();
        assertTrue(res.contains("Страна"));
        assertTrue(res.contains("Область"));
        assertTrue(res.contains("Город"));
        assertTrue(res.contains("Широта"));
        assertTrue(res.contains("Долгота"));
    }
}

package ru.cortan122.georest;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class LocationRequester {
    private final OkHttpClient client = new OkHttpClient();

    /**
     * сделать HTTP запрос
     *
     * @param url ссылка
     * @return полученный текст
     */
    String makeHttpRequest(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    /**
     * парсит JSON
     *
     * @param json JSON код
     * @return полученный JSON объект
     */
    JSONObject parseJson(String json) throws JSONException {
        return new JSONObject(json);
    }

    /**
     * сделать REST API запрос
     *
     * @param url ссылка на REST API endpoint
     * @return полученный JSON объект
     */
    JSONObject makeApiRequest(String url) throws IOException, JSONException {
        return parseJson(makeHttpRequest(url));
    }

    /**
     * @return JSON объект, описывающий нашу локацию
     */
    JSONObject getLocationJson() throws IOException, JSONException {
        return makeApiRequest("https://freegeoip.app/json/");
    }

    /**
     * @return строку, описывающию нашу локацию
     */
    String getLocationString() throws IOException, JSONException {
        var res = getLocationJson();
        return "Страна: " + res.getString("country_name") + "\n" +
                "Область: " + res.getString("region_name") + "\n" +
                "Город: " + res.getString("city") + "\n" +
                "Широта: " + res.getDouble("latitude") + "\n" +
                "Долгота: " + res.getDouble("longitude");
    }

    public static void main(String[] args) {
        try {
            System.out.println(new LocationRequester().getLocationString());
        } catch (JSONException | IOException e) {
            System.err.println("Чтото пошло нетак и нам неудалось найти нашу локацию");
            System.exit(1);
        }
    }
}

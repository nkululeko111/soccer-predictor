package org.soccer;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class BettingAI {

    private static final String API_URL = "http://localhost:5000/predict";
    private HttpClient client;
    private Gson gson;

    public BettingAI() {
        client = HttpClient.newHttpClient();
        gson = new Gson();
    }

    public void predictBettingCode(Map<String, Object> features) {
        try {
            String json = gson.toJson(features);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JsonObject responseJson = gson.fromJson(response.body(), JsonObject.class);
            String prediction = responseJson.get("prediction").getAsString();

            System.out.println("Predicted Betting Code: " + prediction);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

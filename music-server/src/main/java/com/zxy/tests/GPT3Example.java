package com.zxy.tests;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class GPT3Example {

    public static void main(String[] args) {

        String apiKey = "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";
        String prompt = "Hello, how are you?";

        try {
            URL url = new URL("https://api.openai.com/v1/engines/davinci-codex/completions");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + apiKey);
            connection.setDoOutput(true);

            JSONObject requestBody = new JSONObject();
            requestBody.put("prompt", prompt);
            requestBody.put("max_tokens", 64);
            requestBody.put("temperature", 0.5);

            IOUtils.write(requestBody.toString(), connection.getOutputStream(), StandardCharsets.UTF_8);

            Scanner scanner = new Scanner(connection.getInputStream(), StandardCharsets.UTF_8.name());
            String responseBody = scanner.useDelimiter("\\A").next();

            scanner.close();

            JSONObject jsonResponse = new JSONObject(responseBody);

            String generatedText = jsonResponse.getJSONArray("choices").getJSONObject(0).getString("text");

            System.out.println(generatedText);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

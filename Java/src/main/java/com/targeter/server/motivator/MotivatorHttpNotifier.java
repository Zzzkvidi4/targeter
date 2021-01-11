package com.targeter.server.motivator;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class MotivatorHttpNotifier {
    private static final ObjectMapper mapper = new ObjectMapper();

    public void updateSchedule(long targetId) throws IOException {
        URL url = new URL("http://localhost:5000/UpdateSchedule");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setDoOutput(true);

        String json = mapper.writeValueAsString(targetId);
        try (OutputStream os = con.getOutputStream()) {
            byte[] input = json.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }
    }
}

package com.targeter.server.motivator;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@Component
public class MotivatorHttpNotifier {
    private static final ObjectMapper mapper = new ObjectMapper();

    @Value("${targeter.motivation.service.url}")
    private String motivationServiceUrl;

    private static final String UPDATE_SCHEDULE_API_PATH = "%s/UpdateSchedule";

    public void updateSchedule(long targetId) throws IOException {
        URL url = new URL(String.format(UPDATE_SCHEDULE_API_PATH, motivationServiceUrl));
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setDoOutput(true);

        String json = mapper.writeValueAsString(new TargetDto(targetId));
        try (OutputStream os = con.getOutputStream()) {
            byte[] input = json.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }
        con.getResponseCode();
    }

    @Getter
    @AllArgsConstructor
    private static class TargetDto {
        private final long targetId;
    }
}

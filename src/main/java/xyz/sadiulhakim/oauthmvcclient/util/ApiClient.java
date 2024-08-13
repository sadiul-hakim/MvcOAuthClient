package xyz.sadiulhakim.oauthmvcclient.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.client.RestClient;
import xyz.sadiulhakim.oauthmvcclient.user.User;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

public class ApiClient {
    private static final ObjectMapper mapper = new ObjectMapper();

    private ApiClient() {
    }

    public static List<User> callApi(String url, Map<String, String> headers) {
        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpRequest.Builder builder = HttpRequest.newBuilder().GET().uri(URI.create(url));
            headers.forEach(builder::header);
            HttpRequest request = builder.build();
            String body = client.send(request, HttpResponse.BodyHandlers.ofString()).body();

            return mapper.readValue(body, new TypeReference<>() {
            });
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

package fr.lernejo.httpServeur;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;

import java.net.URI;
import java.net.http.HttpResponse;

public class ClientServer {

    public ClientServer(int port, String adversaryUrl) throws IOException, InterruptedException {
        HttpClient currentclient = HttpClient.newHttpClient();
        HttpRequest requetePost = HttpRequest.newBuilder()
            .uri(URI.create(adversaryUrl + "/api/game/start"))
            .setHeader("Accept", "application/json")
            .setHeader("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString("{\"id\":\"1\", \"url\":\"http://localhost:" + port + "\", \"message\":\"hello\"}"))
            .build();
        HttpResponse<String> response = currentclient.send(requetePost, HttpResponse.BodyHandlers.ofString());
    }
}

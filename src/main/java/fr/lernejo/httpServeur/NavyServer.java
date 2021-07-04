package fr.lernejo.httpServeur;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.concurrent.Executors;

import com.google.gson.JsonParser;

import static java.lang.System.exit;


public class NavyServer {
    private final CapitaineDuBateau capitaineDuBateau = new CapitaineDuBateau();
    private final HashMap<String, String> ennemyInfo = new HashMap<>();
    private final ConvertCell convertCell = new ConvertCell();

    public NavyServer(int port) throws IOException {
        HttpServer httpServer = HttpServer.create(new InetSocketAddress("localhost", port), 0);
        httpServer.setExecutor(Executors.newFixedThreadPool(1));
        httpServer.createContext("/ping", new CallHandler());
        httpServer.createContext("/api/game/start", new StartHandler(this));
        httpServer.createContext("/api/game/fire", new FireHandler(this));
        httpServer.start();
    }

    public void startParty(int port, String url) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url + "/api/game/start")).setHeader("Accept", "application/json").setHeader("Content-Type", "application/json").POST(HttpRequest.BodyPublishers.ofString("{\"id\":\""+ this.getCapitaineDuBateau().getId() +"\", \"url\":\"http://localhost:" + port + "\", \"message\":\"Hello\"}")).build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 202) {
                setEnnemyInfo("url", url);
                setEnnemyInfo("port", Integer.toString(port));
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    public void fire() {
        System.out.println("FIRE");
        String cell = this.getCapitaineDuBateau().chooseCell();
        System.out.println(cell);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(getEnnemyInfo().get("url").replace("\"","") + "/api/game/fire?cell=" + cell)).setHeader("Accept", "application/json").setHeader("Content-Type", "application/json").build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 202) {
                this.getCapitaineDuBateau().checkEnnemyMap(this.getConvertCell().convertCellIntoSeaPosition(cell));
                if (JsonParser.parseString(response.body()).getAsJsonObject().get("shipLeft").toString().equals("false")) { System.out.println("WIN"); exit(0); }
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    public CapitaineDuBateau getCapitaineDuBateau() { return this.capitaineDuBateau; }
    public HashMap<String, String> getEnnemyInfo() { return this.ennemyInfo; }
    public void setEnnemyInfo(String key, String value){ this.ennemyInfo.put(key, value); }
    public ConvertCell getConvertCell(){ return this.convertCell; }
}

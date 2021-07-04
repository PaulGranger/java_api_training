package fr.lernejo.httpServeur;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.JSONObject;

public class StartHandler implements HttpHandler {

    private final NavyServer navyServer;

    public StartHandler(NavyServer navyServer)
    {
        this.navyServer = navyServer;
    }
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("POST".equals(exchange.getRequestMethod()))
        {
            try {
                JSONObject jsonObject = (JSONObject) new JSONParser().parse(new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8));
                if (jsonObject.has("id") && jsonObject.has("url") && jsonObject.has("message"))
                {
                    this.newParty(jsonObject, exchange);
                }
                else
                {
                    this.sendMessage(exchange, 400, "JSON TOUT CASSER");
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        else
        {
            this.sendMessage(exchange, 404, "Not Found");
        }
    }

    private void newParty(JSONObject jsonObject, HttpExchange exchange) throws IOException {
        this.navyServer.setEnnemyInfo("url", jsonObject.get("url").toString());
        this.navyServer.setEnnemyInfo("id", jsonObject.get("id").toString());
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.getResponseHeaders().set("Accept", "application/json");
        JSONObject result = new JSONObject();
        result.put("id",this.navyServer.getCapitaineDuBateau().getId());
        result.put("url","http://localhost:" + this.navyServer.getEnnemyInfo().get("port"));
        result.put("message","GL LES ORDIS");
        this.sendMessage(exchange, 202, result.toString());
        this.navyServer.fire();
    }


    private void sendMessage(HttpExchange exchange, int rCode, String message) throws IOException {
        exchange.sendResponseHeaders(rCode, message.length());
        try (OutputStream os = exchange.getResponseBody())
        {
            os.write(message.getBytes());
        }
    }
}

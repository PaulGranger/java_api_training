package fr.lernejo.httpServeur;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONObject;

import javax.security.auth.callback.TextInputCallback;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.regex.Pattern;

import static java.lang.System.exit;

public class FireHandler implements HttpHandler {
    private final NavyServer navyServer;

    public FireHandler(NavyServer navyServer) {
        this.navyServer = navyServer;
    }
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (exchange.getRequestMethod().equals("GET")) {
            String cell = exchange.getRequestURI().getQuery().split("=")[1];
            if (checkCell(cell)) {
                int[] stringArray = this.navyServer.getCapitaineDuBateau().isTouched(this.navyServer.getConvertCell().convertCellIntoSeaPosition(cell));
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("consequence", getConsequences(stringArray[0]));
                jsonObject.put("shipLeft", stringArray[1] == 1);
                sendMessage(exchange,202, jsonObject.toString());
                if (this.navyServer.getCapitaineDuBateau().stillAlive() == 1) { this.navyServer.fire(); }
                else { System.out.println("J'AI PERDU"); exit(0); }
            }
            else System.out.println("JSON TOUT CASSER"); this.sendMessage(exchange, 400, "JSON TOUT CASSER");
        } else { this.sendMessage(exchange, 404, "Not Found"); }
    }

    private String getConsequences(int value)
    {
        return switch (value) {
            case 0 -> "hit";
            case 1 -> "sunk";
            default -> "miss";
        };
    }

    private boolean checkCell(String cell){ return Pattern.matches("[A-J][1-9]$|[A-J]10$", cell); }

    private void sendMessage(HttpExchange exchange, int rCode, String message) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.getResponseHeaders().set("Accept", "application/json");
        exchange.sendResponseHeaders(rCode, message.length());
        try (OutputStream os = exchange.getResponseBody())
        {
            os.write(message.getBytes());
        }
    }
}

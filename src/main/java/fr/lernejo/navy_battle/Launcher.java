package fr.lernejo.navy_battle;

import fr.lernejo.httpServeur.NavyServer;

import java.io.IOException;

public class Launcher {
    public static void main(String[] args) throws IOException {
        if (args.length == 1)
        {
            new NavyServer(Integer.parseInt(args[0]));
        }
        else if (args.length == 2)
        {
            NavyServer navyServer2 = new NavyServer(Integer.parseInt(args[0]));
            navyServer2.startParty(Integer.parseInt(args[0]), args[1]);
        }
    }
}

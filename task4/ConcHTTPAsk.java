import tcpclient.TCPClient;

import java.io.IOException;
import java.net.ServerSocket;

public class ConcHTTPAsk {

    public static void main(String [] args) throws  Exception{
        int port = args.length > 0? Integer.parseInt(args[0]) : 8888;

        ServerSocket connection = new ServerSocket(port);
        while(true) {
            new Thread(new MyRunnable(connection.accept())).start();
        }


        }



    public static String[] checkIfProper(String[] part) throws Exception {

		for(int i = 0; i < part.length; i++)
		System.out.println(part[i]);
        String[] movedStuff = new String[3];

        if ((part[1].contains("/ask"))) {

            for (int i = 0; i < part.length; i++) {
                switch (part[i]) {

                    case "hostname":
                        movedStuff[0] = part[i + 1];
                        break;

                    case "port":
                        movedStuff[1] = part[i + 1];
                        break;

                    case "string":
                        movedStuff[2] = part[i + 1];
                        break;
                }

            }
            if(movedStuff[0] == null || movedStuff[1] == null)
            throw new IOException();

        }
        else {
            for (int i = 0; i < movedStuff.length; i++) {
                System.out.println("Second print: " + i + "" + part[i]);
                part[i] = null;
                movedStuff[i] = null;

            }
            System.out.println("Error: 400");


        }
        return movedStuff;
    }

    public static String sendToTCPClient(String[] movedStuff, String s) throws  Exception {
        System.out.println("sendToTCPClient");
        String returnString;
        System.out.println(movedStuff[0] + movedStuff[1] + movedStuff[2]);

            try {
                returnString = TCPClient.askServer(movedStuff[0], Integer.parseInt(movedStuff[1]), movedStuff[2]);
            } catch (Exception e) {
                s = "HTTP/1.1 404 Not found\r\n\r\n";
                System.out.println("Crash");
                return s;

            }
            s = "HTTP/1.1 200 OK\r\n\r\n";
            return s + returnString;

    }
}


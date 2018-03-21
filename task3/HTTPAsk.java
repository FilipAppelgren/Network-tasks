import tcpclient.*;
import java.net.*;
import java.io.*;

public class HTTPAsk {

    public static void main(String[] args) throws Exception {
        String s;


        int port = args.length > 0? Integer.parseInt(args[0]) : 8888;

        String[] part;
        ServerSocket connection = new ServerSocket(port);

        System.err.println("Server connected to: " + port);


            while (true) {

                try {

                Socket clientSocket = connection.accept();

                BufferedReader inFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter outToClient = new PrintWriter((clientSocket.getOutputStream()));

                String temp = inFromClient.readLine();
                if(temp == null) {
                    outToClient.print("HTTP/1.1 404 Not found\r\n\r\n");
                    outToClient.flush();
                    System.out.println(temp);
                }else {
                    part = temp.split("[?=& ]");

                    s = "HTTP/1.1 400 Bad request\r\n\r\n";
                    String[] movedStuff = checkIfProper(part);
                    if(movedStuff[0] == null || movedStuff[1] == null || movedStuff[2] == null) {
                        outToClient.write("HTTP/1.1 400 Bad request\r\n\r\n");
                        outToClient.flush();
                        inFromClient.close();
                        outToClient.close();
                        clientSocket.close();
                    }

                    outToClient.print(sendToTCPClient(movedStuff, s));
                    outToClient.flush();
                    inFromClient.close();
                    outToClient.close();
                    clientSocket.close();
                }
            }
            catch(IOException e){
                  System.out.print("starcraft is the best");

            }

        }



    }
    public static String[] checkIfProper(String[] part){
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

    public static String sendToTCPClient(String[] movedStuff, String s) throws  Exception{
        String returnString;
        System.out.println(movedStuff[0]+ movedStuff[1]+ movedStuff[2]);
        if(movedStuff[0] == null || movedStuff[1] == null || movedStuff[2] == null)
            return "HTTP/1.1 404 Bad Request\r\n\r\n";
        else {
            System.out.println(movedStuff[0] + movedStuff[1] + movedStuff[2]);
            try {
                returnString = TCPClient.askServer(movedStuff[0], Integer.parseInt(movedStuff[1]), movedStuff[2]);
            }
            catch(IOException e){
                s = "HTTP/1.1 404 Bad Request\r\n\r\n";
               System.out.println("Crash");
                return s;

            }
            s = "HTTP/1.1 200 OK\r\n\r\n";
            return s + returnString;
        }
    }

}


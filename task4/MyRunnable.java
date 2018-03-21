import tcpclient.*;
import java.net.*;
import java.io.*;

public class MyRunnable implements Runnable {

    private  Socket connectionSocket;

    public MyRunnable(Socket parameter) {
        connectionSocket = parameter;
    }

    @Override
    public void run() {


        String s;
        String[] part;


        System.err.println("Server connected");

            try {


                BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
                PrintWriter outToClient = new PrintWriter((connectionSocket.getOutputStream()));

                String temp = inFromClient.readLine();
                if (temp == null) {
                    outToClient.print("HTTP/1.1 404 Not found\r\n\r\n");
                    outToClient.flush();
                    System.out.println(temp);
                } else {
                    part = temp.split("[?=& ]");

                    s = "HTTP/1.1 200 OK\r\n\r\n";
                    String[] movedStuff = ConcHTTPAsk.checkIfProper(part);
                    if (movedStuff[0] == null && movedStuff[1] == null && movedStuff[2] == null) {
                        outToClient.write("HTTP/1.1 400 Bad request\r\n\r\n");
                        outToClient.flush();
                        inFromClient.close();
                        outToClient.close();
                        connectionSocket.close();

                    }

                    outToClient.print(ConcHTTPAsk.sendToTCPClient(movedStuff, s));
                    outToClient.flush();
                    inFromClient.close();
                    outToClient.close();
                    connectionSocket.close();
                }
            } catch (IOException e) {
                System.out.print("starcraft is the best");

            } catch (Exception e) {
                e.printStackTrace();
            }



    }

}


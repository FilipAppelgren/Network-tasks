
import java.net.*;
import java.io.*;

public class HTTPEcho {

    public static void main( String[] args) throws Exception {


        int port = Integer.parseInt(args[0]);

        //String clientSentence;

        ServerSocket connection = new ServerSocket(port);

        Socket clientSocket = connection.accept();

        BufferedReader inFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        DataOutputStream outToClient = new DataOutputStream(clientSocket.getOutputStream());


        System.err.println("Server connected to: " + port);

        while(true){


            String s = "HTTP/1.1 200 OK \r\n\r\n";
            StringBuilder SB = new StringBuilder();
            SB.append(s);

            System.out.println("Pre loop");
            while((s = inFromClient.readLine()) != null && s.length() != 0){
                System.out.println("loopar");
                SB.append(s + "\r\n");
            }
            outToClient.writeBytes(SB.toString());
            clientSocket.close();
            inFromClient.close();
            outToClient.close();



        }


    }
}


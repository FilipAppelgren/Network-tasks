package tcpclient;
import java.io.IOException;
import java.net.*;
import java.io.*;

public class TCPClient {

    public static String askServer(String hostname, int port, String ToServer) throws  IOException {

        if(ToServer == null)
            return askServer(hostname, port);

        StringBuilder modifiedSentence = new StringBuilder();

        Socket connection = new Socket(hostname, port);

        connection.setSoTimeout(2000);

        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        DataOutputStream outToServer = new DataOutputStream(connection.getOutputStream());

        outToServer.writeBytes(ToServer + '\n');

        String tmp;

        try { while((tmp = inFromServer.readLine()) != "\n" && tmp != null) {
            modifiedSentence.append(tmp + '\n');
        }
        connection.close();
        return modifiedSentence.toString();

        }
        catch (IOException e){
            connection.close();
            return modifiedSentence.toString();
        }

    }

    public static String askServer(String hostname, int port) throws  IOException {
        StringBuilder modifiedSentence = new StringBuilder();

        Socket connection = new Socket(hostname, port);

        connection.setSoTimeout(2000);

        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        String tmp;
        int a = 0; //Prevents memory error

        try { while(( tmp = inFromServer.readLine()) != "\n" && tmp != null && a < 1000) {
                modifiedSentence.append(tmp + '\n');
                a++;

        }
            connection.close();
            return modifiedSentence.toString();

        }
        catch (IOException e){
            connection.close();
            return modifiedSentence.toString();
        }

    }
}


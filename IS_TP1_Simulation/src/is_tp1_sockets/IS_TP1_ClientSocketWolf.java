/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package is_tp1_sockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author lemon_ball
 */
public class IS_TP1_ClientSocketWolf {
    private static final int portServer = 4445;
    //private static final String host = "127.0.0.1";
    private static final String host = "localhost";
    
    public String connectToSocket(String s) throws IOException{
        Socket socket = new Socket(host, portServer);
        //1. Send to server
        PrintWriter pw = new PrintWriter(socket.getOutputStream());
        pw.write(s);
        pw.flush();
        socket.shutdownOutput();
        //2. Get from server
        InputStreamReader isr = new InputStreamReader(socket.getInputStream());
        BufferedReader br = new BufferedReader(isr);
        String line = null;
        StringBuilder sb = new StringBuilder();
        while((line = br.readLine()) != null){
            sb.append(line);
        }
        
        System.out.println("CLIENT RECEIVED: "+ sb.toString());
        
        //3. CLOSE
        socket.shutdownInput();
        pw.close();
        br.close();
        isr.close();
        socket.close();
        
        return sb.toString();
    }
}

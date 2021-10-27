/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package is_tp1_sockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author lemon_ball
 */
public class IS_TP1_ClientSocketCow {
    private static final int portServer = 4444;
    //private static final String host = "127.0.0.1";
    private static final String host = "localhost";

    /**
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    /*public static void main(String[] args) throws Exception {
        IS_TP1_ClientSocketCow server = new IS_TP1_ClientSocketCow();
        
    }*/
/*
    public void sendToServer(String s) throws IOException {
        Socket socket = new Socket(host, portServer);
        OutputStream os = socket.getOutputStream();
        PrintWriter pw = new PrintWriter(os);
        System.out.println("CLIENT WILL SEND: " + s);
        pw.write(s);
        pw.flush();
        socket.shutdownOutput();
        pw.close();
        os.close();
        socket.close();
    }
    
    public String getFromServer() throws IOException{
        Socket socket = new Socket(host, portServer);
        InputStream is = socket.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String line = null;
        StringBuilder sb = new StringBuilder();
        while((line = br.readLine()) != null){
            sb.append(line);
        }
        System.out.println("CLIENT RECEIVED: "+ sb.toString());
        socket.shutdownInput();
        br.close();
        isr.close();
        is.close();
        socket.close();
        
        return sb.toString();
    }
    */
    
    public String connectToSocket(String s) throws IOException{
        Socket socket = new Socket(host, portServer);
        //1. Send to server
        //OutputStream os = socket.getOutputStream();
        PrintWriter pw = new PrintWriter(socket.getOutputStream());
        //System.out.println("CLIENT WILL SEND: " + s);
        pw.write(s);
        //pw.write("/n");
        pw.flush();
        socket.shutdownOutput();
        //2. Get from server
        //InputStream is = socket.getInputStream();
        InputStreamReader isr = new InputStreamReader(socket.getInputStream());
        BufferedReader br = new BufferedReader(isr);
        String line = null;
        StringBuilder sb = new StringBuilder();
        while((line = br.readLine()) != null){
            //System.out.println("CLIENT LINE: "+line);
            sb.append(line);
        }
        
        System.out.println("CLIENT RECEIVED: "+ sb.toString());
        
        //3. CLOSE
        socket.shutdownInput();
        pw.close();
        //os.close();
        br.close();
        isr.close();
        //is.close();
        socket.close();
        
        return sb.toString();
    }
}

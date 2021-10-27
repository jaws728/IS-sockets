/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package is_tp1_sockets;

import Common.MessageManagement;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;
import org.netbeans.xml.schema.updateschema.TMyPlace;
import org.netbeans.xml.schema.updateschema.TPlace;
import org.netbeans.xml.schema.updateschema.TPosition;

/**
 *
 * @author adroc
 */
public class IS_TP1_ServerSocketCow {

    private static final int portServer = 4444;

    /**
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {
        IS_TP1_ServerSocketCow server = new IS_TP1_ServerSocketCow();
        server.run();
    }

    public void run() throws Exception {

        //TODO - Lab3
        //Start your server socket
        //Read content received from client (Simulation)
        //Calculate new Cow position
        //Send new cow position to the client (Simulation)
        /*---------------------------------------------------------*/
        
        //1. Create a server
        ServerSocket serverSocket = new ServerSocket(portServer);
        System.out.println("***Server starting***");
        
        /************************IN THEREADS**************************/
        ExecutorService threadPool = Executors.newFixedThreadPool(100);
        
        while(true){
            //2. Listen to a server: accept()
            Socket socket = serverSocket.accept();
            
            Runnable runnable = ()->{
            
            //3. Get input stream and read the client server's information
            //InputStream is = socket.getInputStream();
            
                try {
                    InputStreamReader isr = new InputStreamReader(socket.getInputStream());
                    BufferedReader br = new BufferedReader(isr);
                    //convert buffered data to string
                    String line = null;
                    StringBuilder sb = new StringBuilder();
                    System.out.println("Cow server entered...");
                    while((line = br.readLine()) != null){
                        sb.append(line);
                    }
                    System.out.println("SERVER RECEIVED: "+ sb.toString());

                    //Change Position
                    String changed = updateCowPosition(sb.toString());
                    //String changed = "hey i am here";

                    //4. Send back to client server
                    //OutputStream os = socket.getOutputStream();
                    PrintWriter pw = new PrintWriter(socket.getOutputStream());
                    pw.write(changed);
                    //pw.write("/n");
                    pw.flush();

                    //5. Close all
                    //socket.shutdownInput(); //shut down input stream
                    //socket.shutdownOutput();
                    br.close();
                    pw.close();
                    isr.close();
                    //os.close();
                    //is.close();
                    socket.close();
                } catch (IOException ex) {
                    Logger.getLogger(IS_TP1_ServerSocketCow.class.getName()).log(Level.SEVERE, null, ex);
                } catch (JAXBException ex) {
                    Logger.getLogger(IS_TP1_ServerSocketCow.class.getName()).log(Level.SEVERE, null, ex);
                }

            //serverSocket.close();
            };
            threadPool.submit(runnable);
        }
    }
    
    private String updateCowPosition(String s) throws JAXBException{
        //Unmarshal
        MessageManagement mm = new MessageManagement();
        TMyPlace currentMyPlace = mm.retrievePlaceStateObject(s);
        
        ArrayList<Integer> safePlaces = new ArrayList<>();
        for(int i=1; i<9; i++){
            TPlace mp = currentMyPlace.getPlace().get(i);
            //set conditions
            //System.out.println("pos-x: "+ mp.getPosition().getXx());
            if (mp.getPosition() == null)
                continue;
            int x = mp.getPosition().getXx();
            int y = mp.getPosition().getYy();
            if ((x == 14) && (i==3 || i==4 || i==5))
                continue;
            if ((x == 0) && (i==1 || i==8 || i==7))
                continue;
            if ((y == 14) &&(i==1 || i==2 || i==3))
                continue;
            if ((y == 0) && (i==5 || i==6 || i==7))
                continue;
            
            if (!(mp.isCow() || mp.isDog() || mp.isObstacle()|| mp.isWolf())){    
                safePlaces.add(i);
            }
        }
        
        //pick an random number from previous saved array
        int rand = new Random().nextInt(safePlaces.size());
        //System.out.println("Length: "+ safePlaces.size()); //length need to be attached and not declared in the begginnig
        TPosition tp = currentMyPlace.getPlace().get(safePlaces.get(rand)).getPosition();
        TMyPlace new_myPlace = currentMyPlace;
        new_myPlace.getPlace().get(0).setPosition(tp);
        
        //Marshal and return
        return mm.createPlaceStateContent(new_myPlace);
    }
}

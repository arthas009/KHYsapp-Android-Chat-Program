
package khysappserver;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
public class ListenWithThread implements Runnable{
    int portNumber;
    public void startListening(int portNumber)
    {
        Serverfuncts sfuncts = new Serverfuncts();
        ServerSocket serverSocket = null;
        Socket clientSocket;
            try
            {
            serverSocket = new ServerSocket(portNumber);
            clientSocket = serverSocket.accept();
            BufferedReader in = sfuncts.OpenBuffReader(clientSocket); 
            BufferedWriter BW = sfuncts.OpenBuffWriter(clientSocket);
            PrintWriter out = new PrintWriter(BW,true); 
                while(true)
                {
                      String str = in.readLine();
                          if(str.startsWith("LogIn:"))
                          {
                              sfuncts.LogIn(out,str);
                              break;
                          }
                          else if(str.startsWith("InsertUser:"))
                          {  
                              sfuncts.InsertNewUser(out, str);
                              break;
                          }             
                          else if(str.startsWith("SendMessage:"))
                          {
                              sfuncts.SendMessage(out, str);
                          }
                          else if(str.startsWith("DrawMessages:"))
                          {
                              sfuncts.DrawMessages(out, str);
                          }
                          else if(str.startsWith("CheckMessages:"))
                          {
                              sfuncts.CheckForMessages(out, str);
                          }       
                }
             serverSocket.close();
             clientSocket.close();   
            } 
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
       
        finally
        {
            try {
                serverSocket.close();
            } catch (IOException ex) {
                Logger.getLogger(ListenWithThread.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("The client may be closed Listening Again..");
            startListening(portNumber);
        }
    }
    //////////////////////////////////////CONSTRUCTORS AND RUN FUNCTION ////////////////////////////////////////////////////////////////////7
    public ListenWithThread(int portNumber){
     this.portNumber=portNumber;
    }

    @Override
    public void run(){
            startListening(portNumber);
    }
}

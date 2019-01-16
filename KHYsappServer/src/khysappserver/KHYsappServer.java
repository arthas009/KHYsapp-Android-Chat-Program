
package khysappserver;
import java.io.IOException;
public class KHYsappServer {
    
public static void main(String[] args) throws IOException, InterruptedException {
   
        int portNumber =7788;
        startListening(portNumber);
        runCheck("server");
    }
  public static void startListening(int portNumber) throws IOException
  { 
    Thread ListenWithThread1 = new Thread(new ListenWithThread(portNumber));
    ListenWithThread1.start();
  }   
  public static void runCheck(String obj) throws InterruptedException
  {
    while(true)
        {
            System.out.println("The "+obj+" is still running programmer !");
            Thread.sleep(5000);
        }
  }
  
}

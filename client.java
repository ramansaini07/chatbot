
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class client {
    Socket socket;

    BufferedReader br; //br for input stream
    PrintWriter out;
    //constructor
    public client(){
        try {
            System.out.println("Sending request to the server..");
            socket = new Socket("127.0.0.1",7777);
            System.out.println("connection Done!");

            br=new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out=new PrintWriter(socket.getOutputStream(),true);
            
            startReading();
            startWriting();

            
        } catch (Exception e) {
        }

    }
    
    public void startReading(){
        //thread reading
        Runnable r1=()->{
            System.out.println("reader started..");
            while(true){
                try {
                    String msg =br.readLine();
                    if (msg.equals("exit"))
                    {
                        System.out.println("Server terminated the chat");
                        break;
                    }
                    System.out.println("Server :" +msg);
                } catch (IOException ex) {
                }

            }
        };
        new Thread(r1).start();
    }
    public void startWriting(){
        //thread acceptiong data
        Runnable r2=()->{
            System.out.println("Writer started");
            while(true){
                try {
                    BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                    String content = br1.readLine();
                    out.println(content);          
                    out.flush();
                } catch (IOException ex) {
                }

            }
        };
        new Thread(r2).start();


    }
    
    public static void main(String[] args) {
        System.out.println("this is client..");
        new client();
    }
}

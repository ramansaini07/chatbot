
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

class server{

    ServerSocket server;
    Socket socket;

    BufferedReader br; //br for input stream
    PrintWriter out; //out for output

    //constructor
    public server(){

        try {
            server= new ServerSocket(7777);
            System.out.println("Server is running....");
            System.out.println("waiting..");
            socket=server.accept();

            br=new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out=new PrintWriter(socket.getOutputStream(),true);
            
            startReading();
            startWriting();

        } catch (IOException e) {
            e.printStackTrace();
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
                        System.out.println("Client terminated the chat");
                        
                        break;
                    }
                    System.out.println("Client :" +msg);
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

    public static void main(String[] args){
        System.out.println("This is server ..going to start ");
        new server();//object
    } 
}
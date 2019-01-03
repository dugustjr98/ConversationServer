import com.sun.security.ntlm.Server;

import java.net.ServerSocket;
import java.net.Socket;

public class ConServer {
    private int port;
    private ConHouse conHouse;

    public ConServer(int port){
        this.port = port;
        conHouse = new ConHouse();
    }

    public void run(){
        ServerSocket serverSocket = null;

        try{
            serverSocket = new ServerSocket(port);
            while(true){
                Socket socket = serverSocket.accept();
                ConServerHandler conServerHandler = new ConServerHandler(socket,conHouse);
                conServerHandler.start();
            }
        }catch (Exception ex){
            System.out.println("오류 발생");
        }finally {
            try{serverSocket.close();}catch (Exception ignore){}
        }
    }
}

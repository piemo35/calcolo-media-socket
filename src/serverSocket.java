import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.io.*;

public class serverSocket {

    private final int port = 6754;

    public Socket getServerSocket() throws IOException {
        return (new ServerSocket(port)).accept();
    }

    public void startServer(Socket socket) throws IOException {
        
    	System.out.println("new connection");

        while (true){
            String data = readData(socket); //leggo i valori che arrivano
            
            System.out.println("Client : " + data); //printo i valori
            
            int[] array = Arrays.stream(data.split("\\s+")).mapToInt(Integer::parseInt).toArray();
            
            sendData(socket,String.valueOf(avg(array))); // rimando i dati al client
            
            if (data.contains(" 0")){ //controllo se e uguale a 0
                socket.close();
                System.out.println( "Connection closed" );
                return;
            }            
        }
    }

    
    public static String readData(Socket socket) throws IOException {
        return (new BufferedReader( new InputStreamReader( socket.getInputStream() ))).readLine();
    }


    public static void sendData(Socket socket, String data) throws IOException {
        (new PrintWriter( socket.getOutputStream(), true)).println(data);
    }
    
    public float avg(int[] values) {
    	
    	int sum = 0;
    	int count = 0;
    	
    	for(int value : values) // for each values as value
    		if (value != 0) {
    			sum += value; 
    			count++;
    		}   		
    	return sum/(float)count;
    }

    public static void main(String[] args) throws IOException{        
    	serverSocket server = new serverSocket();
    	server.startServer(server.getServerSocket());
    }
}

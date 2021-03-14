import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.io.*;


public class clientSocket {
	
	public Socket serverSocket;
	
	clientSocket() throws IOException{
		serverSocket = new Socket ("localhost",6754);
	}
	
    public String inputDaTastiera() throws IOException{
        //leggo i dati da tastiera
    	System.out.print("Choose a number:_ ");
        InputStreamReader reader = new InputStreamReader(System.in);        
        BufferedReader dato = new BufferedReader(reader);
        return dato.readLine();
    }

    
    public void startClient(Socket socket) throws IOException {

        while (true){   
        	
        	String numero = inputDaTastiera(); //leggo da tastiera	           
            sendData(socket, numero); //mando i dati al server               
            String risultato = readData(socket); //leggo la media che arriva dal server           
            System.out.println("Media: "+ risultato);
            
            if(numero.contains(" 0")) {
            	socket.close();
                System.out.println( "Connection closed");
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

    public static void main(String[] args) throws IOException {
        clientSocket client = new clientSocket();
        client.startClient(client.serverSocket);
    }
}

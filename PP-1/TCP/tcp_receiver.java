import java.net.*; 
import java.io.*; 
import java.util.*;

public class tcp_receiver 
{ 
	 public static void main(String[] args) throws IOException 
	{ 
	    ArrayList<String> datastore = new ArrayList<>();//Array for storing the received values
		ServerSocket serverSocket = null; 

	    try 
		{ 
	         serverSocket = new ServerSocket(8777); 
	    } 
	    catch (IOException e) 
		{ 
			 System.err.println("Connection error"); 
			 System.exit(1); 
		} 

	    Socket clientSocket = null; 	    

	    try 
		{ 
	         clientSocket = serverSocket.accept(); 
	    } 
	    catch (IOException e) 
		{ 
			 System.err.println("Accept failed."); 
			 System.exit(1); 
		} 

	    System.out.println ("Successfully Connected");
	   

	    PrintWriter output = new PrintWriter(clientSocket.getOutputStream(),true); 
	    BufferedReader input = new BufferedReader(new InputStreamReader( clientSocket.getInputStream())); 

	    String inputfromsender;		

	    while ((inputfromsender = input.readLine()) != null) 
	    { 
	        System.out.println ("Server received following \n" + inputfromsender);
			datastore.add(inputfromsender);
	        output.println(inputfromsender); 
	 	} 

		//System.out.println("Contents of datastore: " + datastore);
		
	    output.close(); 
	    input.close(); 
	    clientSocket.close(); 
	    serverSocket.close(); 
	} 
} 
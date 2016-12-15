import java.io.*;
import java.net.*;
import java.util.*;

public class udp_receiver 
{
	public static void main(String args[]) 
	{
		DatagramSocket sock = null;
		ArrayList<String> datastore = new ArrayList<>();

		try 
		{
			sock = new DatagramSocket(8777);
			System.out.println ("Waiting to be connected");
		}
		catch (IOException e) 
		{ 
			 System.err.println("Connection error"); 
			 System.exit(1); 
		} 	
		
			
		
		byte[] buffer = new byte[1200];
		DatagramPacket input = new DatagramPacket(buffer, buffer.length);//creating space for the input datagram
		
		try
		{
			while (true) 
			{
				//Receive datagram from sender
				sock.receive(input);
				byte[] data = input.getData();									 
				String s = new String(data, 0, input.getLength());
				System.out.println("\n Received following data: "+s);
				
				//Storing the values in an array of strings
				datastore.add(s);
				
				//create a datagram to send to the client
                DatagramPacket dp = new DatagramPacket(s.getBytes(),s.getBytes().length, input.getAddress(),input.getPort());
				
				//send the datagram to the sender
				sock.send(dp);
			}
		}
		catch (IOException e) 
		{ 
			 System.err.println("IOException " + e); 
		}
		
			
	}

}
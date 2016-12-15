package Client;
import java.io.*;
import java.net.*;
import java.util.*;  
import java.lang.*; 

public class myclient 
{
	public static void main( String[] args ) throws Exception
	{

		String serverName = args[0];
		int port = Integer.parseInt(args[1]);
		String command = args[2];
		String filePath = args[3];
		int filesize=6022386;
		
		try
		{   	
			Socket socket = new Socket(serverName, port);    
			System.out.println("Connected to " + socket.getRemoteSocketAddress() + "\n");
				
			ObjectOutputStream OutputStream = new ObjectOutputStream(socket.getOutputStream());   
			ObjectInputStream InputStream = new ObjectInputStream(socket.getInputStream());                      
		
			if (command.equals("GET")) 
			{
				OutputStream.writeObject(command + " " + filePath + " HTTP/1.1");

				String status = (String) InputStream.readObject();
				System.out.println("Status: " + status + "\n");
				
				if ( status.equals("200 OK")) 
				{
					String fileName = (String) InputStream.readObject();
					System.out.println("Contents of the file " + fileName + " :\n");
					
		
					try
					{
						String line = (String) InputStream.readObject();
						while (line != null && line.length() > 0) 
						{
							System.out.println(line);
							line = (String) InputStream.readObject();
						}
					}
					
					catch(EOFException e)
					{
						System.out.println("\nFile received\n");
					}
				}
				
				else if ( status.equals("404 Not Found")) 
				{
					System.out.println("File Not Found!\n");
				}
				
			}

			else if (command.equals("PUT")) 
			{
				File file = new File(filePath); 
				if (!(file.isDirectory()) && (file.exists()))
				{
					OutputStream.writeObject(command + " " + filePath + " HTTP/1.1");
					System.out.println("Sending the file: " + file.getName() + "\n");
					OutputStream.writeObject(file.getName());  
		
					FileInputStream fis = new FileInputStream(file);  
					byte [] buffer = new byte[filesize];
					Integer bytesRead = 0;  

					while ((bytesRead = fis.read(buffer)) > 0) 
					{  
						OutputStream.writeObject(bytesRead);  
						OutputStream.writeObject(Arrays.copyOf(buffer, buffer.length));  
					}  
					
					System.out.println("File: " + file.getName() + "Sent\n");					
					System.out.println("Status from server: " + InputStream.readObject() + "\n");
					System.out.println("Status Message:" + InputStream.readObject() + "\n");
				}
				
				else
				{
					System.out.println("File doesn't exist\n");
					OutputStream.writeObject("Invalid command");
				}
			}
			
			else
			{
				System.out.println("Invalid HTTP\n");
				OutputStream.writeObject("Invalid HTTP");
			}
			
			System.out.println("Terminating Connection to" + socket.getRemoteSocketAddress());
			OutputStream.close();
			InputStream.close();
			socket.close();
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
	}
}


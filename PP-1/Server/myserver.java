package Server;
import java.net.*;
import java.io.*;
import java.util.*;  
import java.lang.*; 


public class myserver extends Thread
{
	private int port;
	private ServerSocket serverSocket;
	public static final int BUFFER_SIZE = 100;
	public myserver(int Temp) throws IOException
	{
		port=Temp;
		System.out.println( "Starting server on port: " + port + "\n");
		serverSocket = new ServerSocket( port);
		serverSocket.setSoTimeout(120000);
	}

	@Override
	public void run()
	{
		System.out.println( "Running on port: " + port + "\n");
		while( true )
		{
			try
			{	
				System.out.println("Waiting for client on port " + serverSocket.getLocalPort() + "...");
				Socket server = serverSocket.accept();
				System.out.println("Connected to " + server.getRemoteSocketAddress() + " through the port " + serverSocket.getLocalPort() +"\n");
				ObjectInputStream in= new ObjectInputStream(server.getInputStream());  
				ObjectOutputStream out = new ObjectOutputStream(server.getOutputStream());
				String cmd=(String) in.readObject();
				String[] Command = cmd.split(" ");
				String method = Command[0];
				String filePath = Command[1];
				String httpVersion = Command[2];
				if ( method.equals("GET")) 
				{
					File file = new File(filePath);
					if ( !(file.isDirectory()) && (file.exists()) )
					{
						out.writeObject("200 OK");
						out.writeObject(file.getName());						
						System.out.println("Sending file content of " + file.getName() + "\n");
						BufferedReader buffer = new BufferedReader(new FileReader(file));
						String line = null;						
						while ((line = buffer.readLine())!= null) 
						{
							out.writeObject(line);
						}
						
						System.out.println("Contents of file " + file.getName() + " sent \n");	
						buffer.close();
					}
					else
					{
						out.writeObject("404 Not Found");
						out.writeObject("File: " + file.getName() + " doesn't exist");
						System.out.println("File: " + file.getName() + " doesn't exist\n");
					}
				
				}
				else if ( method.equals("PUT")) 
				{
					try
					{
						byte [] buffer = new byte[BUFFER_SIZE]; 						
						Object object = in.readObject(); 
						filePath = "Server\\" + object.toString();
						FileOutputStream fos = null;
						if (object instanceof String) 
						{  
							fos = new FileOutputStream(filePath);  
						} 
						else 
						{  
							throwException("Error!");  
						}  
	
						Integer bytesRead = 0; 
						object = in.readObject();  
	
						if (!(object instanceof Integer)) 
						{  
							throwException("Error. Read object should be of type Integer");  
						}  

						bytesRead = (Integer)object;	
						object = in.readObject(); 	
						
						if (!(object instanceof byte[])) 
						{  
							throwException("Error. Read object should be of type Byte");  
						}
						
							buffer = (byte[])object;	 
							fos.write(buffer, 0, bytesRead);
					
						
						File file = new File(filePath);
						if ( !(file.isDirectory()) && (file.exists()) )
						{
							System.out.println("File saved.\n");
							out.writeObject("200 OK");
							out.writeObject("File saved on server.");					
						}
						else
						{
							System.out.println("Error! File not found\n");
							out.writeObject("404 Not Found");
							out.writeObject("File not saved in server.");			
						}
						fos.close(); 
					}

					catch(Exception e)
					{
						System.out.println("\n");
					}
				}

				else
				{
					System.out.println("Invalid Method\n");
				}				
				
				System.out.println("Terminating connection to " + server.getRemoteSocketAddress());
				in.close();
				out.close();				 
				server.close();
			}

			catch(SocketTimeoutException s)
			{
				System.out.println("Socket timed out!");
				break;
			}

			catch(IOException e)
			{
				e.printStackTrace();
				break;
			}

			catch(Exception e)  
			{
				e.printStackTrace();
				break;
			}
		}
	}

	public static void throwException(String message) throws Exception 
	{  
		throw new Exception(message);  
	}  
  
	
public static void main( String[] args )
{
	int[] portArray = new int[100];
		
		for (int i=0; i < args.length; i++)
		{
			portArray[i] = Integer.parseInt(args[i]);
		}
		
		try
		{   myserver serverThread;
		
			for (int i=0; i < args.length; i++)
			{   
				serverThread = new myserver(portArray[i]);
				serverThread.start();
			}
		}  

		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
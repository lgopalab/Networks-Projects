import java.io.*;
import java.net.*;
import java.util.*;

public class tcp_sender 
{

    public static void main(String args[]) throws IOException 
	{
        Socket sock = null;
        int port = 8777;
        String s;
        BufferedReader socketinput = null;
        PrintWriter socketoutput = null;
        double max = 0;
        double totalDelay = 0;
        double avg = 0f;
        long delay;
		int zerodelay = 0;
		int max_at_iteration = 0;
        int ITERATIONS = 0;//Set number of iteratins here
        int MSG_SIZE = 0; //Set message size here
		Scanner reader = new Scanner(System.in);  // Reading from System.in
		System.out.println("Enter number of Iterations: ");
		ITERATIONS = reader.nextInt();
		System.out.println("Enter Message size: ");
		MSG_SIZE = reader.nextInt();
        try 
		{
            sock = new Socket("192.168.2.11", 8777);
            socketinput = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            socketoutput = new PrintWriter(sock.getOutputStream(), true);
             
            for (int i = 1; i <= ITERATIONS; i++) 
			{   s = i + "";
                for (int j = 0; j < MSG_SIZE; j++) 
				{
                    s += 'A';
                }

                long startTime = Calendar.getInstance().getTimeInMillis(); 
                socketoutput.println(s);
                String receivedoutput = socketinput.readLine();
                long endTime = Calendar.getInstance().getTimeInMillis();
                System.out.println("\nIteration " + i);
                System.out.println("Received Output: " + receivedoutput);
                System.out.println("StartTime = " + startTime);
                System.out.println("EndTime = " + endTime);
                
				delay = endTime - startTime;
                
				float ETE=delay/2;                
                
                totalDelay = totalDelay + ETE;   // Calculating total delay
                
				if(ETE > max) 
				{
                    max = ETE;
					max_at_iteration = i;
                }
				if(ETE == 0) 
				{
                    zerodelay++;
                }
                
            }
            
            avg = totalDelay / ITERATIONS; // finding the average of all the delays
            
			System.out.println("Maximum ETE: " + max );//Printing the Maximum ETE
            System.out.println("Average Time: " + avg);//Printing the Average ETE
            System.out.println("Max ETE at: " + max_at_iteration);// Printing where MAX ETE occured
			System.out.println("Zero Delay: " + zerodelay);// Printing number of Zero Delays
        } 
		
		catch (IOException e) {
            System.err.println("IOException " + e);
        } 
		
		finally {
            socketinput.close();
            socketoutput.close();
        }

    }
}


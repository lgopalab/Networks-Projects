import java.util.Arrays;
public class Entity2 extends Entity
{    
    // Perform any necessary initialization in the constructor
    public Entity2()
    {
      for(int i = 0; i < 4; i++)
	  {
        for(int j = 0; j < 4; j++)
		{
          distanceTable[i][j] = 999;
        }
      }
      
      distanceTable[2][0] = 3;
      distanceTable[2][1] = 1;
      distanceTable[2][2] = 0;
      distanceTable[2][3] = 2;    
 
  	  Packet dtPacket1 = new Packet(2, 0, distanceTable[2]);
	  NetworkSimulator.toLayer2(dtPacket1);
	  Packet dtPacket2 = new Packet(2, 1, distanceTable[2]);
	  NetworkSimulator.toLayer2(dtPacket2);
	  Packet dtPacket3 = new Packet(2, 3, distanceTable[2]);
	  NetworkSimulator.toLayer2(dtPacket3);
      
     
      System.out.println("Entity2 Initializion Complete at" + NetworkSimulator.time + ". Distance Table is:");
      printDT();
    }
    

    public void update(Packet p)
    {
      boolean send = false;     
     
	  for(int i = 0; i<4; i++)
	  {
        if(p.getMincost(i)+distanceTable[2][p.getSource()] < distanceTable[i][p.getSource()])
		{          
          distanceTable[i][p.getSource()] = p.getMincost(i)+distanceTable[2][p.getSource()];  
		  
          if(distanceTable[i][p.getSource()]<distanceTable[2][i])
		  {
            distanceTable[2][i] = distanceTable[i][p.getSource()];
            send = true;
          }
        }
      }
      
	 
      if(send)
	  {
		Packet dtPacket1 = new Packet(2, 0, distanceTable[2]);
		NetworkSimulator.toLayer2(dtPacket1);
		Packet dtPacket2 = new Packet(2, 1, distanceTable[2]);
		NetworkSimulator.toLayer2(dtPacket2);
		Packet dtPacket3 = new Packet(2, 3, distanceTable[2]);
		NetworkSimulator.toLayer2(dtPacket3);
	  }
		
      
      System.out.println("Entity2 Update Complete at " + NetworkSimulator.time + ". Distance Table is:");
      printDT();
    }
    
    public void linkCostChangeHandler(int whichLink, int newCost)
    {
      
    }
    
    public void printDT()
    {
        System.out.println();
        System.out.println("           via");
        System.out.println(" D2 |   0   1   3");
        System.out.println("----+------------");
        for (int i = 0; i < NetworkSimulator.NUMENTITIES; i++)
        {
            if (i == 2)
            {
                continue;
            }
            
            System.out.print("   " + i + "|");
            for (int j = 0; j < NetworkSimulator.NUMENTITIES; j++)
            {
                if (j == 2)
                {
                    continue;
                }
                
                if (distanceTable[i][j] < 10)
                {    
                    System.out.print("   ");
                }
                else if (distanceTable[i][j] < 100)
                {
                    System.out.print("  ");
                }
                else 
                {
                    System.out.print(" ");
                }
                
                System.out.print(distanceTable[i][j]);
            }
            System.out.println();
        }
    }
}

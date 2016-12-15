import java.util.Arrays;
public class Entity3 extends Entity
{    
    // Perform any necessary initialization in the constructor
    public Entity3()
    {
      for(int i = 0; i < 4; i++)
	  {
        for(int j = 0; j < 4; j++)
		{
          distanceTable[i][j] = 999;
        }
      }
      
      distanceTable[3][0] = 7;
      distanceTable[3][1] = 999;
      distanceTable[3][2] = 2;
      distanceTable[3][3] = 0;    
 
      Packet dtPacket1 = new Packet(3, 0, distanceTable[3]);
      NetworkSimulator.toLayer2(dtPacket1);
      Packet dtPacket2 = new Packet(3, 2, distanceTable[3]);
      NetworkSimulator.toLayer2(dtPacket2);
     
      System.out.println("Entity3 Initializion Complete at " + NetworkSimulator.time + ". Distance Table is:");
      printDT();
    }
    
   
    public void update(Packet p)
    {
      boolean send = false;     
	  
	  for(int i = 0; i<4; i++)
	  {
        if(p.getMincost(i)+distanceTable[3][p.getSource()] < distanceTable[i][p.getSource()])
		{          
          distanceTable[i][p.getSource()] = p.getMincost(i)+distanceTable[3][p.getSource()];  
		  
          if(distanceTable[i][p.getSource()]<distanceTable[3][i])
		  {
            distanceTable[3][i] = distanceTable[i][p.getSource()];
            send = true;
          }
        }
      }
      
	 
      if(send)
	  {
		Packet dtPacket1 = new Packet(3, 0, distanceTable[3]);
		NetworkSimulator.toLayer2(dtPacket1);
		Packet dtPacket2 = new Packet(3, 2, distanceTable[3]);
		NetworkSimulator.toLayer2(dtPacket2);
      }
      
      System.out.println("Entity1 Update Complete at " + NetworkSimulator.time + ". Distance Table is:");
      printDT();
    }
    
    public void linkCostChangeHandler(int whichLink, int newCost)
    {
     
    }
    
    public void printDT()
    {
        System.out.println();
		System.out.println("         via");
        System.out.println(" D3 |   0   2");
        System.out.println("----+--------");
        for (int i = 0; i < NetworkSimulator.NUMENTITIES; i++)
        {
            if (i == 3)
            {
                continue;
            }
            
            System.out.print("   " + i + "|");
            for (int j = 0; j < NetworkSimulator.NUMENTITIES; j += 2)
            {
               
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

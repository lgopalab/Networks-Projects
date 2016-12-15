import java.util.Arrays;
public class Entity0 extends Entity
{    
    
    public Entity0()
    {
      
      for(int i = 0; i < 4; i++)
	  {
        for(int j = 0; j < 4; j++)
		{
          distanceTable[i][j] = 999;
        }
      }
      
		distanceTable[0][0] = 0;
		distanceTable[0][1] = 1;
		distanceTable[0][2] = 2;
		distanceTable[0][3] = 7;    

		Packet dtPacket1 = new Packet(0, 1, distanceTable[0]);
		NetworkSimulator.toLayer2(dtPacket1);
		Packet dtPacket2 = new Packet(0, 2, distanceTable[0]);
		NetworkSimulator.toLayer2(dtPacket2);
		Packet dtPacket3 = new Packet(0, 3, distanceTable[0]);
		NetworkSimulator.toLayer2(dtPacket3);
     
      System.out.println("Entity0 Initializion Complete at " + NetworkSimulator.time + ". Distance Table is:");
      printDT();
    }
    
   
    public void update(Packet p)
    { 
      boolean send = false;   
		      
	  for(int i = 0; i<4; i++)
	  {
        if(p.getMincost(i)+distanceTable[0][p.getSource()] < distanceTable[i][p.getSource()])
		{          
          distanceTable[i][p.getSource()] = p.getMincost(i)+distanceTable[0][p.getSource()];  
		  
          if(distanceTable[i][p.getSource()]<distanceTable[0][i])
		  {
            distanceTable[0][i] = distanceTable[i][p.getSource()];
            send = true;
          }
        }
      }
      
	 
      if(send)
	  {
        Packet dtPacket1 = new Packet(0, 1, distanceTable[0]);
		NetworkSimulator.toLayer2(dtPacket1);
		Packet dtPacket2 = new Packet(0, 2, distanceTable[0]);
		NetworkSimulator.toLayer2(dtPacket2);
		Packet dtPacket3 = new Packet(0, 3, distanceTable[0]);
		NetworkSimulator.toLayer2(dtPacket3);
      }
      
      System.out.println("Entity0 Update Complete at " + NetworkSimulator.time + ". Distance Table is:");
      printDT();
    }
    
    public void linkCostChangeHandler(int whichLink, int newCost)
    {
      
    }
    
    public void printDT()
    {
        System.out.println();
        System.out.println("           via");
        System.out.println(" D0 |   1   2   3");
        System.out.println("----+------------");
        for (int i = 1; i < NetworkSimulator.NUMENTITIES; i++)
        {
            System.out.print("   " + i + "|");
            for (int j = 1; j < NetworkSimulator.NUMENTITIES; j++)
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

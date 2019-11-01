import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class UDPSocketServer 
{
	DatagramSocket socket = null;
	
	public UDPSocketServer() 
	{
	
	}
	
	public void createAndListenSocket()
	{
		System.out.println("----------Server is Online" );
		try 
		{
			ArrayList<InetAddress> prevClient = new ArrayList<InetAddress>();
			ArrayList<Client> database = new ArrayList<Client>();
			boolean repeat = false;
			boolean repeat2 = true;
			socket = new DatagramSocket(9998);
			byte[] incomingData = new byte[1024];
			int clientNo = 1; 
			int TTL = 40;
			
			while (true)
			{	
				Timer timer = new Timer();     
				TimerTask task = new TimerTask()
                {           
        			public void run()
                    {
        				Client buffClient = new Client();
        				boolean shouldDelete = false;
        				Client buff = new Client();
        				for( Client temp : database)
        				{
        					if(temp.getStatus() <= 0)
        					{        						
        						buffClient = temp;
                        		shouldDelete = true;
                        		break;
        					}
        					else
        					{
            					temp.delStatus();
        					}
        	            }  
        				        				
        				if(shouldDelete)
        				{
        					database.remove(buffClient);
        					System.out.println("Client " +buffClient.getAddress() + " " + buffClient.getPort() + " has gone offline :( " 
        							+ " Online: " + database.size() );
        				}

    					if(database.isEmpty())
    					{
    						timer.cancel();
    					}
                    }
                 };
                 
                 long delay = 1000;
                 long intevalPeriod = 1000; 
                 timer.scheduleAtFixedRate(task, delay, intevalPeriod);
                 
				DatagramPacket incomingPacket = new DatagramPacket(incomingData, incomingData.length);
				socket.receive(incomingPacket);
				byte[] data = incomingPacket.getData();
				InetAddress newClientIp = incomingPacket.getAddress();
                int newClientPort = incomingPacket.getPort();
                Client client = new Client(clientNo, newClientPort, newClientIp, TTL);             
                
                for( Client temp : database)
                {
                	//if(temp.getAddress().equals(newClientIp))
                	if(temp.getPort() == newClientPort)
	                {                		
                		temp.setStatus(TTL);
                		repeat = true;                		
                		System.out.println("Client: "+ client.getAddress() + " " + client.getPort() + " has sent a packet. " );
	                }
                }
                
                if( repeat == false)
               {
                	for(InetAddress temp : prevClient)
                	{
                		if(temp.equals(client.getAddress()))
                		{
                			
                			repeat2 = false;
                			System.out.println(client.getAddress() + " is our preivious client welcome back :)"); 
                		}
                		else
                		{
                			repeat = true;
                		}
                	}
                	                	
                	database.add(client);
	                clientNo++;
	                System.out.println("Client: "+ client.getAddress() + " " + client.getPort() + " has joined the chat. Online: " 
	                		+ database.size());
                }
				
                if(repeat2 == true)
                {
                	prevClient.add(client.getAddress());
                }
                
                
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
				ObjectOutputStream os = new ObjectOutputStream(outputStream);
				os.writeObject(database);
				byte[] sending = outputStream.toByteArray();
				//System.out.println("Sending to: " + temp1.getPort() + " about " + temp.getPort());
				DatagramPacket replyPacket =
						new DatagramPacket(sending, sending.length, client.getAddress(), client.getPort());
				socket.send(replyPacket);

				
				repeat2 = false;
				repeat = false;
				//System.exit(0);
			}
			
		} 
		catch (SocketException e) 
		{
			e.printStackTrace();
		}
		catch (IOException i) 
		{
			i.printStackTrace();
		} 
	}
	
	public static void main(String[] args)
	{
		UDPSocketServer server = new UDPSocketServer();
		server.createAndListenSocket();
	}
}


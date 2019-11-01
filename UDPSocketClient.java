
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class UDPSocketClient 
{
DatagramSocket Socket;
	
	public UDPSocketClient() 
	{
	
	}

	public void createAndListenSocket()
	{
		System.out.println("---------Client");
		try 
		{	
			boolean repeat = false;
			ArrayList<Client> database = new ArrayList<Client>();
			Socket = new DatagramSocket();
			byte[] incomingData = new byte[1024];
			InetAddress IPAddress = InetAddress.getByName("localhost");
			String sentence = "I am on";
	        byte[] data = sentence.getBytes();
			DatagramPacket sendPacket = new DatagramPacket(data, data.length, IPAddress, 9998);
			System.out.println("Data Incoming.....");
			TimerTask task = new TimerTask()
	        {
				public void run()
	            {
					DatagramPacket sendPacket = new DatagramPacket(data, data.length, IPAddress, 9998);
	     			try
	     			{
							Socket.send(sendPacket);
					}
	     			catch (IOException e) 
	     			{
	     				e.printStackTrace();
					}
	            }
	         };
	         Timer timer = new Timer();
	         long delay = 0;
	         long intevalPeriod = 10*1000; 
	         timer.scheduleAtFixedRate(task, delay, intevalPeriod);
	         
			while(true)
			{
				
				//sending objects
				//ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
				//ObjectOutputStream os = new ObjectOutputStream(outputStream);
				//os.writeObject(client);
				//byte[] data = outputStream.toByteArray();
				
				/*recieving data
				socket.receive(incomingPacket);
				byte[] data = incomingPacket.getData();
				ByteArrayInputStream in = new ByteArrayInputStream(data);
				ObjectInputStream is = new ObjectInputStream(in);
				try
				{
					Client client = (Client) is.readObject();
				}
				catch (ClassNotFoundException e)
				{
					e.printStackTrace();
				}
				*/	
							
		         DatagramPacket incomingPacket = new DatagramPacket(incomingData, incomingData.length);
		         Socket.receive(incomingPacket);
		         byte[] serverBuffer = incomingPacket.getData();
		         ByteArrayInputStream in = new ByteArrayInputStream(serverBuffer);
		         ObjectInputStream is = new ObjectInputStream(in);
		         try
		         {
					ArrayList<Client> client = (ArrayList<Client>) is.readObject();
					database = client;
					for( Client temp : database)
					{
						System.out.println("Clients online: " + temp.getAddress() + " " + temp.getPort() + " Online Clients: " 
								+ database.size() );
					}
					
		        }
		         
				catch (ClassNotFoundException e)
				{
					e.printStackTrace();
				}
		         repeat = false;
			} 
		}
		catch (UnknownHostException e) 
		{
			e.printStackTrace();
		} 
		catch (SocketException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
}

public static void main(String[] args)
{
	UDPSocketClient client = new UDPSocketClient();
	client.createAndListenSocket();
}
}

import java.io.Serializable;
import java.net.InetAddress;

public class Client implements Serializable 
{
	private static final long serialVersionUID = 1L;
	private int id;
	private int port;
	private InetAddress ipAddress;
	private int status;
	
	/* default constructor*/
	public Client()
	{
		
	}
	
	//constructor
	public Client(int id, int port, InetAddress ipAddress, int status)
	{
		this.id = id;
		this.port = port;
		this.ipAddress = ipAddress;
		this.status = status;
	}
	
	
	public int getId() 
	{
		return id;
	}
	
	public void setId(int id) 
	{
		this.id = id;
	}
	
	public int getPort() 
	{
		return port;
	}
	
	public void setPort(int name) 
	{
		this.port = name;
	}
	
	public InetAddress getAddress() 
	{
		return ipAddress;
	}
	
	public void setAddress(InetAddress addressLine)
	{
		this.ipAddress = addressLine;
	}
	
	public int getStatus() 
	{
		return status;
	}
	
	public void delStatus() 
	{
		status--;
	}
	
	public void setStatus(int status) 
	{
		this.status = status;
	}
	
	public String toString() 
	{
		return "Client: " + getId() + " Port: " + getPort() + " IP:" + getAddress();
	}
}

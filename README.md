
High Availability Cluster (HAC)  is a group of hosts that acts like a single system and provide continuous uptime. It is often used for
load balancing, backup and failover aspect of the network. A functional HAC must provide all the hosts in the cluster with access to the
same shared storage which allows a host to failover to another host without any downtime in the event of failure.

For this project we had to create a HAC using UDP  that would run both on P2P or Client-Server connection structure.

Peer to Peer:
Our Peer to Peer class consists of  two methods namely startServer and startSender. Both these methods are run in two seperate threads at
the start of the code. The IP address of all the clients are hardcoded in ArrayList object. Here, if a client is up, it would broadcast 
its status to all IP address in the ArrayList. The for loop checks the list and checks if the client in the ArrayList is not reachable. 
Then the server side of the Peer to Peer would check to see if it is available in the network, if not it would let all the other clients 
know that it is down with broadcast.


Server to Client connection:
In this connection the client first sends a packet saying it is on from which the server accepts and stores its information as a client 
object which and stores it’s IP address, port number, client number information, and it’s online status. The IP address of the server is
hard coded in the client’s code. Then after storing the client’s information, server re-sends the database to every client in the database.
This exchange of information keeps on happening between the client and the server every 30 seconds. If the server does not receive any 
packet within 10 sec it thinks the server has died and deletes it from the database however the information of every client is stored in 
the prevClient arraylist. In case if the dead client comes backs online then the server recognizes this and acknowledge it as an old client
and informs “Is our previous client welcome back :) ”. If another client is online then the server adds the information to the database and
sends that information to every client. This happens every time there is a new client. Clients also stores the database in their own 
database and displays that information.

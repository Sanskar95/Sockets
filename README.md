# Simple Chat With Java Sockets

This repository includes two classes which can be started: `ChatServer` and `ChatClient`. 

The `ChatServer` listens to incoming requests on a given port and registers clients once they try to connect. If a 
client sends a message over its socket, the server broadcasts it to the other registered clients.

A `ChatClient` tries to create a connection to a given server on a given port. It then waits to print messages from the 
server on the console and can send messages to the server (which should be broadcasted to the other registered clients.

Starting a `ChatServer` requires the port number as an argument. After compiling the file, tt can be started with 
`java ChatServer <port-number>`. Each `ChatClient` can then ce started with the address of the node where the server is 
running (e.g. 127.0.0.1 in case of localhost) and the port to which the server listens. After compiling the client, it 
can be started with `java ChatClient <host-address> <port-number>`.

## TCP Communication

The following picture represents the start of a `ChatServer` and three instances of the class `ChatClient`.

![TCP Example With Three Clients](https://github.com/Sanskar95/Sockets/blob/master/tcp-example.PNG "TCP Example With Three Clients")

The server listens to connections on port 5000. The first three lines represent the TCP calls when the first client 
starts. The three lines show the three-way TCP handshake with the flags `SYN`, `SYN, ACK` and `ACK`. Also, the ports 
of the client can be seen. The same happens in line 4 to 6 for the second client and in line 7 
to 9 for the third client.

Then, the third client sends a message (line 10). It gets delivered with the flag `PSH` which means 'push' and tells 
the receiver to process the received packets. To acknowledge the receipt, the receiver sends back a message with the 
flag `ACK`. The same happens for the other clients because the server broadcasts the message to each other client.

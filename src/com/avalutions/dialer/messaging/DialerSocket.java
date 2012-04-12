package com.avalutions.dialer.messaging;

import java.net.*;
import java.util.*;
import java.io.*;

public class DialerSocket {
	private final int BUFFER_SIZE = 1024;
	private final String BAD_CHAR = String.valueOf('\0');
	
	private Socket socket;
	private Queue<String> pendingMessages;
	
	private BufferedReader reader;
	private PrintWriter writer;
	
	private char[] buffer;
	private String theRest;
	private int seperator;
	
	DialerSocket(String address, int port) {
		try {
			socket = new Socket(address, port);
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			writer = new PrintWriter(socket.getOutputStream());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		pendingMessages = new LinkedList<String>();
		buffer = new char[BUFFER_SIZE];
		receive();
	}

    private void receive() {
    	try {
			while(reader.read(buffer) > 0) {
			    //Clear the messages waiting in the queue if there are any.
			    while (pendingMessages.size() > 0)
			    {
			        //Take the message out of the queue since we are going to send it out
			        String message = pendingMessages.poll();
			        //ASYNCHRONOUSLY invoke the message delegate with the message from the queue
			        //ThreadPool.QueueUserWorkItem(new WaitCallback((state) => this._messageHandler.Invoke(message)));
			    }
			    
			    String messagePart = new String(buffer);
			    messagePart = messagePart.replace(BAD_CHAR, "");
			    StringBuffer fullMessage = new StringBuffer();
			    fullMessage.append(theRest);
			    fullMessage.append(messagePart);

                //While the index of the seperator (LINE_END defined & initiated as private member)d
                while ((seperator = fullMessage.indexOf(String.valueOf(MessageSeperator.Terminator.getCharacter()))) > 0)
                {
                    //Pull out the first message available (up to the seperator index
                    String message = fullMessage.substring(0, seperator);
                    //Queue up our new message
                    pendingMessages.add(message);
                    //Take out our line end character
                    fullMessage = fullMessage.delete(0, seperator + 1);
                }
                
                theRest = fullMessage.toString();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    boolean send(String message) {
    	if(socket.isConnected()) {
    		writer.print(message);
    		return true;
    	}
    	return false;
    }
}

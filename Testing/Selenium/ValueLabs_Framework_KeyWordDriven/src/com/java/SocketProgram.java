package com.java;

import java.io.IOException;
import java.net.ServerSocket;

public class SocketProgram {
	
	public static void main(String args[]) {
		boolean portTaken = false;
	    ServerSocket socket = null;
	    try {
	        socket = new ServerSocket(7777);
	        socket.close();
	        socket = new ServerSocket(7777);
	    } catch (IOException e) {
	        portTaken = true;
	        System.out.println(portTaken);
	    } finally {
	    	System.out.println(portTaken);
	        if (socket != null)
	            try {
	                socket.close();
	            } catch (IOException e) { /* e.printStackTrace(); */ }
	    }
	}
}

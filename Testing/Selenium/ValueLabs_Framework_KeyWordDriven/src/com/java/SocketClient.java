package com.java;
import java.net.*;
import java.io.*;
 class SocketClient 
 {
  SocketClient(String NodeIP,String AutoIt) throws Exception
	{
	Socket s = new Socket(NodeIP.toString(),777);
	OutputStream os = s.getOutputStream();
	DataOutputStream dos = new DataOutputStream(os);
	InputStream Is = s.getInputStream();
	DataInputStream dis = new DataInputStream(Is);
	dos.writeBytes(AutoIt+ "\n");
	System.out.println(dis.readLine());
	dis.close();
	dos.close();
	s.close();
	}

}

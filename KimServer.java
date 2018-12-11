//2018 FALL COSC 732 group project. Written by Myungsik kim
//Implement Localhost server
package socket01;

import java.awt.Image;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.imageio.ImageIO;

public class server {
	public static final int serverPort = 40001;

	public void run(){

		try {
			ServerSocket ss = new ServerSocket(serverPort);
			System.out.println("wating..");
			
			while(true) {
				Socket s = ss.accept();
				System.out.println(s.getInetAddress() + " connected!");
			
				FileInputStream fis = new FileInputStream("C:\\Users\\Administrator\\eclipse-workspace\\socket01\\image\\Facebook-thumbs-up.jpg");
				//System.out.println(fis);
				byte[] buffer = new byte[fis.available()];
				fis.read(buffer);
				String log = new String(buffer);
				System.out.println(log);
				ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
				oos.writeObject(buffer);
				System.out.println("image sent!");
				oos.flush();
				oos.close();
				s.close();
				System.out.println("socket closed");
				ss.close();
				System.out.println("server closed");
			}
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		server doit = new server();
		doit.run();
	}
} 
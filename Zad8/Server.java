import java.io.*;
import java.net.Socket;
import java.net.ServerSocket;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.FilterInputStream;
import java.time.*;

public class Server
{
	ServerSocket serverSocket;
	boolean running = true;
	
	public static void main(String[] args)
	{
		new Server(999).notificationHandler();
	}
	
	public Server(int port)
	{
		try
		{
			serverSocket = new ServerSocket(port);
			System.out.println("Server started at " + port + " port");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void notificationHandler()
	{
		try
		{
			while(running)
			{
				Socket client = serverSocket.accept();
				Thread notifyThread = new Thread(new NotificationThread(client));
				notifyThread.start();
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		
	}

	public class NotificationThread implements Runnable
	{
		Socket socket;
		DataOutputStream output;
		DataInputStream input;
		
		public NotificationThread(Socket _socket)
		{
			socket = _socket;
		}
		
		@Override
		public void run()
		{
			try
			{
				int hours, minutes;
				String[] divided_info;
				output = new DataOutputStream(socket.getOutputStream());
				input = new DataInputStream(socket.getInputStream());
				while(input.available() == 0)
				{
					try
					{
						Thread.sleep(1);
					}
					catch (InterruptedException e)
					{
						e.printStackTrace();
					}
				}
				String note = input.readUTF();
				
				divided_info = note.split("[: ]");
				hours = Integer.parseInt(divided_info[0]);
				minutes = Integer.parseInt(divided_info[1]);
				int _minute = 0, _hour = 0;
				
				do
				{
					LocalTime maybe = LocalTime.now();
					
					_minute = maybe.getMinute();
					_hour = maybe.getHour();
					
					try
					{
						Thread.sleep(1);
					}
					catch (InterruptedException e)
					{
						e.printStackTrace();
					}

				}while(hours != _hour || minutes != _minute);
				
				String notification = new String();
				for(int i = 2; i < divided_info.length; i++)
				{
					notification = notification.concat(divided_info[i]);
					notification = notification.concat(" ");
				}
					
				notification = notification.trim();
				output.writeUTF(notification);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		
		
		
	}
}
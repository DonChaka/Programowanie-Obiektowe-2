import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.FilterInputStream;

public class Client
{
	Socket socket;
	DataOutputStream server_output;
	DataInputStream server_input;
	
	public void notification(String ip, int port) throws IOException
	{
		String statement;
        String[] divided_info;
        int hours, minutes;
        Scanner user_input = new Scanner(System.in);
        Pattern pattern = Pattern.compile("\\d\\d:\\d\\d .+");
        Matcher match;
		
		for(;;)
		{
			try
			{
				this.socket = new Socket(ip, port);
				server_output = new DataOutputStream(socket.getOutputStream());
				server_input = new DataInputStream(socket.getInputStream());
				
				System.out.println("Please input time and notification");
				
				
				statement = user_input.nextLine();
				match = pattern.matcher(statement);
				
				if(match.matches())
				{
					divided_info = statement.split("[: ]");
					hours = Integer.parseInt(divided_info[0]);
                    minutes = Integer.parseInt(divided_info[1]);

                    if (hours >= 0 && hours < 24 && minutes >= 0 && minutes < 60) 
					{
                        server_output.writeUTF(statement);
						server_output.flush(); 
						while(server_input.available() == 0)
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
						String note = server_input.readUTF();
						System.out.println("It is time!\n");
						System.out.println(note);
						
						
                    } else 
					{
                        System.out.println("Wrong time format. Please try again");
                    }
				}
				else
				{
					throw new NotificationFormatException();
				}
				socket.close();
				
			}
			catch (NotificationFormatException e)
			{
				System.out.println("Wrong input format. Please try again");
			}
			catch (IOException e)
			{
				throw e;
			}
			
		}
	}
	
	public static void main(String[] args) throws IOException
	{
		
		try 
		{
            Client client = new Client();
            client.notification("127.0.0.1", 999);
        } 
		catch (IOException e) 
		{
            e.printStackTrace();
        }
		
	}
	
}
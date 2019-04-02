import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;

public class Main
{
	
	public static void main(String[] args) throws Exception
	{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String line = "";
		
		System.out.println("Podaj nazwe pliku");
		FileHandler file = new FileHandler(in.readLine());

		while (line.equalsIgnoreCase("quit") == false)			
		{
			line = in.readLine();
			
			System.out.println(file.getRandomLenPart());
		}
		
		in.close();

	}
	
}

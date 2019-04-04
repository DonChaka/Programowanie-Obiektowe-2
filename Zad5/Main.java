import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;

public class Main
{
	
	public static void main(String[] args) throws Exception
	{
		try
		{
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			String line = "";
			
			System.out.println("Podaj nazwe pliku");
			String name = new String(in.readLine());
			FileHandler file = new FileHandler(name);
			
			nioStreamClass saveName = new nioStreamClass("fileName.txt");
			saveName.writeFile(name);

			while (line.equalsIgnoreCase("quit") == false)			
			{
				line = in.readLine();
				
				System.out.println(file.getRandomLenPart());
			}
			
			in.close();
		}
		catch(StringIndexOutOfBoundsException e)
		{
			System.out.println("End of file. \nProgram will exit");
		}
		catch(FileNotFoundException e)
		{
			System.out.println("File not found");
		}
		catch(Exception e)
		{
			System.out.println("Unexpected Exception");
		}
	}
	
}

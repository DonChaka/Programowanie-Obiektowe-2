import java.nio.*;
import java.nio.file.*;
import java.util.*;


public class FileHandler
{
	private int offset;
	private Path path;
	String file;
	
	public FileHandler(String name) throws Exception
	{
		Path path = Paths.get(name);
		
		List<String> preformat = Files.readAllLines(path);
		file = new String();
		
		for(String line : preformat)
			file += line;
	}
	
	public String getRandomLenPart() throws Exception
	{
		Random dice = new Random();
		int roll = dice.nextInt(5) + 1;
		offset += roll;
		
		return file.substring(offset-roll, offset);
	}
	
}
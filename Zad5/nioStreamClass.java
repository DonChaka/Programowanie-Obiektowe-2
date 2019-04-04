import java.nio.*;
import java.nio.file.*;
import java.util.*;

public class nioStreamClass
{
	Path path;
	
	public nioStreamClass(String path) 
	{
		this.path = Paths.get(path);
	}
	
	public String readFile() throws Exception
	{
		List<String> preformat = Files.readAllLines(path);
		String file = new String();
		
		for(String line : preformat)
			file += line + "\n";
		
		return file;
	}
	
	public void writeFile(String toWrite) throws Exception
	{
		Files.write(this.path, toWrite.getBytes());
	}
}
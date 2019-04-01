import java.io.*;

public class ioStreamClass
{
	String path;
	FileInputStream input = null;
	FileOutputStream output;
	
	public ioStreamClass(String path) 
	{
		this.path = path;
	}
	
	public String readFile() throws Exception
	{
		FileInputStream input = new FileInputStream(this.path);
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));
		String tempLine = new String();
		String zwrot = new String();
		
		while((tempLine = reader.readLine()) != null)
			zwrot += tempLine;
		
		return zwrot;
	}
	
	public void writeFile(String toWrite) throws Exception
	{
		FileOutputStream output = new FileOutputStream(this.path);
		output.write(toWrite.getBytes());
	}
}
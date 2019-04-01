public class Main
{
	public static void main(String[] args)
	{
		try
		{
			String iopath = "iotest.txt";
			String niopath = "niotest.txt";
			int n = 50;
			RandomStringGenerator text = new RandomStringGenerator(n);
			String textToSave = new String(text.generate());
			String zwrot = new String();
			
			
			ioStreamClass ioStr = new ioStreamClass(iopath);
			long ioStart = System.nanoTime();
			ioStr.writeFile(textToSave);
			zwrot = ioStr.readFile();
			long ioStop = System.nanoTime();
			System.out.println("java.io wykonala zadanie w  " +
								(ioStop - ioStart) +
								"nano sekund");
								
			nioStreamClass nioStr = new nioStreamClass(niopath);
			long nioStart = System.nanoTime();
			nioStr.writeFile(textToSave);
			zwrot = nioStr.readFile();
			long nioStop = System.nanoTime();
			System.out.println("java.nio wykonala zadanie w " +
								(nioStop - nioStart) +
								"nano sekund");
			
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
	
}
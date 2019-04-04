public class Main
{
	public static void main(String[] args)
	{
		try
		{
			String iopath = "iotest.txt";
			String niopath = "niotest.txt";
			final int numberOfGeneratedCharacters = 1000;
			RandomStringGenerator text = new RandomStringGenerator(numberOfGeneratedCharacters);
			String textToSave = new String(text.generate());
			String readed = new String();
			
			
			ioStreamClass ioStr = new ioStreamClass(iopath);
			long ioStart = System.nanoTime();
			
			long ioWriteStart = System.nanoTime();
			ioStr.writeFile(textToSave);
			long ioWriteTotal = System.nanoTime() - ioWriteStart;
			
			long ioReadStart = System.nanoTime();
			readed = ioStr.readFile();
			long ioReadTotal = System.nanoTime() - ioReadStart;
			
			long ioStop = System.nanoTime();
			System.out.println("\njava.io odczytal dane w " +
								ioReadTotal +
								" nano sekund \nzapisal dane w " +
								ioWriteTotal + 
								"nano sekund \nCala operacja zajela: " + 
								(ioStop - ioStart) +
								"nano sekund\n");
								
			nioStreamClass nioStr = new nioStreamClass(niopath);
			long nioStart = System.nanoTime();
			
			long nioWriteStart = System.nanoTime();
			nioStr.writeFile(textToSave);
			long nioWriteTotal = System.nanoTime() - nioWriteStart;
			
			long nioReadStart = System.nanoTime();
			readed = nioStr.readFile();
			long nioReadTotal = System.nanoTime() - nioReadStart;
			
			long nioStop = System.nanoTime();
			System.out.println("java.nio odczytal dane w  " +
								nioReadTotal +
								" nano sekund \nzapisal dane w " +
								nioWriteTotal + 
								"nano sekund \nCala operacja zajela: " + 
								(nioStop - nioStart) +
								"nano sekund\n");
			
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
	
}
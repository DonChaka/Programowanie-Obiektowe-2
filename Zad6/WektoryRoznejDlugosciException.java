class WektoryRoznejDlugosciException extends Exception 
{
	public int aSize;
	public int bSize;
	
	public WektoryRoznejDlugosciException(int aSize, int bSize) 
	{
		super("Vectors have different sizes");
		this.aSize = aSize;
		this.bSize = bSize;
	}
	
}

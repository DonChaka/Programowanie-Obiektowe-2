public class Zadanie2
{
	int a,b;
	String text;

	public Zadanie2(String text, int a, int b)
	{
		this.text = new String(text);
		this.b = b;
		this.a = a;
	}
	
	public void execute() throws Exception;
	{
		if(a < 0 || a > text.length() || b < a || b > text.length()) 
			throw new IndexOutOfBoundsException();
		for(int i = this.a; i <= this.b; i++)
			System.out.print(this.text.charAt(i));
	}
}
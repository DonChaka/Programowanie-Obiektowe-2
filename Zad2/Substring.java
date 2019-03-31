class Substring
{
	public static void main(String args[])
	{
		try
		{
			Zadanie2 hello = new Zadanie2(
			args[0], 
			Integer.parseInt(args[1]), 
			Integer.parseInt(args[2]));
			
			hello.execute();
		}
		catch(IndexOutOfBoundsException e)
		{
			System.out.println("Index out of bounds");
		}
		catch(NumberFormatException e)
		{
			System.out.println("Argumenty sa zlego formatu!");
		}
	}
}
class Main
{
	public static void main(String[] args) throws Exception
	{
		try
		{
			QuadraticFunction fcja = new QuadraticFunction(
			Integer.parseInt(args[0]),
			Integer.parseInt(args[1]),
			Integer.parseInt(args[2]));
			
			fcja.calculateSolutions();
			System.out.println(fcja.solutionsAnalysis());
			
		}
		catch (IndexOutOfBoundsException e)
		{
			System.out.println("Too few arguments");
		}
		catch (NumberFormatException e)
		{
			System.out.println("Wrong format arguments");
		}
	}
}
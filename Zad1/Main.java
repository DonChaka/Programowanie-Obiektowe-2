class Main
{
	public static void main(String[] args) throws Exception
	{
		try
		{
			QuadraticFunction quadraticEquation = new QuadraticFunction(
			Integer.parseInt(args[0]),
			Integer.parseInt(args[1]),
			Integer.parseInt(args[2]));
			
			quadraticEquation.calculateSolutions();
			System.out.println(quadraticEquation.solutionsAnalysis());
			
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

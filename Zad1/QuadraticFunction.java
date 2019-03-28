import java.lang.*;

public class QuadraticFunction
{
	int a,b,c;
	double delta;
	int solutinCounter;
	double x1,x2,x0;
	
	public QuadraticFunction(int a, int b, int c) throws Exception
	{
		this.a = a;
		if (a == 0) throw new WrongFormulaExeption("a = 0 it`s not quadratic function");
		this.b = b;
		this.c = c;
		this.calculateDelta();
	}
	
	private void calculateDelta()
	{
		delta = b*b - 4 * a * c;
		if(delta == 0) solutinCounter = 1;
		else if(delta  > 0) solutinCounter = 2;
		else solutinCounter = 0;
	}
	
	public void calculateSolutions() throws Exception
	{
		switch(solutinCounter)
		{	
			case 0:
			break;
			
			case 1:
			x0 = -b / (2*a);
			break;
			
			case 2:
			x1 = -b + Math.sqrt(this.delta) / (2 * a);
			x2 = -b - Math.sqrt(this.delta) / (2 * a);
			break;
			
			default:
			throw new CriticalException("Data Error");
		}
	}
	
	public String solutionsAnalysis() throws Exception
	{
		switch(solutinCounter)
		{	
			case 0:
			return "No solutions in real numbers";
			
			case 1:
			return "x0 = " + Double.toString(x0); 
			
			case 2:
			return "x1 = " + Double.toString(x1) + "\nx2 = " + Double.toString(x2);
			
			default:
			throw new CriticalException("Data Error");
		}
	}
}

class CriticalException extends Exception
{
	public CriticalException(String msg) 
	{
		super(msg);
	}
}

class WrongFormulaExeption extends Exception
{
	public WrongFormulaExeption(String msg)
	{
		super(msg);
	}
}
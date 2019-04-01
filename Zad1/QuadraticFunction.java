import java.lang.*;
import java.text.DecimalFormat;

public class QuadraticFunction
{
	int a,b,c;
	double delta;
	int solutionCounter;
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
		if(delta == 0) solutionCounter = 1;
		else if(delta  > 0) solutionCounter = 2;
		else solutionCounter = 0;
	}
	
	public void calculateSolutions() throws Exception
	{
		switch(solutionCounter)
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
		DecimalFormat dec = new DecimalFormat("#0.00");
		switch(solutionCounter)
		{	
			case 0:
			String complex = new String();
			complex += "x1 = " + dec.format(-b/(2*a));
			
			if(a < 0)
				complex += " + " + dec.format(Math.sqrt(Math.abs(delta))) + "i\n";
			else
				complex += " - " + dec.format(Math.sqrt(Math.abs(delta))) + "i\n";
			
			complex += "x2 = " + dec.format(-b/(2*a));
			if(a > 0)
				complex += " + " + dec.format(Math.sqrt(Math.abs(delta))) + "i\n";
			else
				complex += " - " + dec.format(Math.sqrt(Math.abs(delta))) + "i\n";
			
			return complex;
			
			
			case 1:
			return "x0 = " + dec.format(x0); 
			
			case 2:
			return "x1 = " + dec.format(x1) + "\nx2 = " + dec.format(x2);
			
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

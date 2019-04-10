import java.util.*;
import java.io.*;

public class Main
{
	public static void main(String[] args)
	{
		try
		{
			boolean notDone = true;
			Vector<Double> A = new Vector<Double>();
			Vector<Double> B = new Vector<Double>();
			Vector<Double> C = new Vector<Double>();
			VectorHandler handler = new VectorHandler();
			
			do
			{
			
				System.out.print("Please enter first vector: ");
				A = handler.readVector();
				System.out.print("Please enter second vector: ");
				B = handler.readVector();	
				try
				{
					C = handler.addVectors(A, B);
					notDone = false;
				}
				catch(WektoryRoznejDlugosciException e)
				{
					System.out.println("Error: " + e.getMessage() + " " + e.aSize + " is different than " + e.bSize);
					System.out.println("Please enter vectors once again");
				}
			}while(notDone);
			
			handler.saveVector("result.txt", C);
		}
		catch(IOException e)
		{
			System.out.println("IO error: " + e.getLocalizedMessage());
		}
		catch(Exception e)
		{
			System.out.println("Critical Error: " + e.getMessage());	
		}
	}
}

import java.util.*;
import java.io.*;

public class VectorHandler
{
	void saveVector(String path, Vector<Double> S) throws Exception
	{
		PrintWriter printer = new PrintWriter(new File(path));
		for(int i = 0; i < S.size(); i++)
			printer.write(Double.toString(S.elementAt(i)) + " ");
		
		printer.close();
	}
	
	Vector<Double> addVectors(Vector<Double> A, Vector<Double> B) throws Exception
	{
		if(A.size() != B.size())
			throw new WektoryRoznejDlugosciException(A.size(), B.size());
		
		Vector<Double> C = new Vector<Double>(A.size());
		
		for(int i = 0; i < A.size(); i++)
			C.add(A.elementAt(i) + B.elementAt(i));
		
		return C;
	}
	
	Vector<Double> readVector() throws Exception
	{
		InputStreamReader inputStream = new InputStreamReader(System.in);
		BufferedReader reader = new BufferedReader(inputStream);
		Scanner strainer = new Scanner(reader.readLine());
		Vector<Double> preparing = new Vector<Double>();
		
		while(strainer.hasNext())
		{
			if(strainer.hasNextDouble())
			{
				preparing.add(strainer.nextDouble());
			}
			else
			{
				strainer.next();
			}
		}
		strainer.close();
		
		return preparing;
	}
	
	
}
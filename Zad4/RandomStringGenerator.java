import java.util.*;

public class RandomStringGenerator
{
	int iloscZnakow;
	
	public RandomStringGenerator(int ilosc) throws Exception
	{
		if(ilosc < 0) throw new WrongArgsException("Negative number of characters");
		this.iloscZnakow = ilosc;
	}
	
	public String generate()
	{
		StringBuilder rsg = new StringBuilder(iloscZnakow);
		String alphanumerics = "QERTYUIOPASFGHJKLZXCVBNM1234567890qwertyuiopasdfghjklzxcvbnm";
		Random dice = new Random();
	
		for(int i = 0; i < this.iloscZnakow; i++)
			rsg.append(alphanumerics.charAt(dice.nextInt(alphanumerics.length())));
			
		return rsg.toString();
	}
	
}
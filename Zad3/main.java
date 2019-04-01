import java.util.Random;
import java.util.Scanner;

class main
{
	public static void main(String[] args)
	{
		Scanner input = new Scanner(System.in);
		Random d100 = new Random();
		int nr, guess, proby = 0;
		boolean gram = false;
		do
		{
			proby = 0;
			nr = d100.nextInt(101);
			System.out.println("Pomyslalem sobie liczbe od 0 do 100 zgadnij jaka to liczba (na potrzeby testow jest to: " + nr + ")");
			
			do
			{
				proby++;
				guess = input.nextInt();
				
				if(guess < nr)
					System.out.println("Za malo");
				else if(guess > nr)
					System.out.println("Za duzo");
				
			}while(guess != nr);
				
			System.out.println("Brawo! Udalo co sie za " + proby + " razem");
			System.out.println("Czy chcesz zagrac jeszcze raz? [y]");
			char wybor = input.next().charAt(0);
			if(wybor == 'y')
				gram = true;
			else
				gram = false;
			
		}while(gram);
	}
}
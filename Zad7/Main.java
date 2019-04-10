import java.util.Map;
import java.util.TreeMap;
import java.util.Set; 

public class Main
{
	
	public static void main(String[] args)
	{
		Osoba osoba1 = new Osoba("Adam", "Kowalski", "Warszawa Tokarska 12", new NrTelefoniczny("+48", "531459450"));
		Osoba osoba2 = new Osoba("Sherlock", "Holmes", "Londyn Baker Street 221B", new NrTelefoniczny("+44", "27121887"));
		Osoba osoba3 = new Osoba("Andrzej", "Sapkowski", "Lodz Rivska 8", new NrTelefoniczny("+48", "21061948"));

		Firma firma1 = new Firma("Polskie Radio Program III", "Warszawa Mysliwiecka 3/5/7", new NrTelefoniczny("+48", "223333399"));
		Firma firma2 = new Firma("CD Project Red", "Warszawa Jagiellonska 74", new NrTelefoniczny("+48", "225196900"));
		Firma firma3 = new Firma("Politechnika Lodzka", "Lodz Zeromskiego 116", new NrTelefoniczny("+48", "426365522"));
		
		TreeMap <NrTelefoniczny, Wpis> ksiazkaTelefoniczna = new TreeMap<NrTelefoniczny, Wpis>();
		
		ksiazkaTelefoniczna.put(osoba1.nrTelefonu, osoba1);
        ksiazkaTelefoniczna.put(osoba2.nrTelefonu, osoba2);
        ksiazkaTelefoniczna.put(osoba3.nrTelefonu, osoba3);

        ksiazkaTelefoniczna.put(firma1.nrTelefonu, firma1);
        ksiazkaTelefoniczna.put(firma2.nrTelefonu, firma2);
        ksiazkaTelefoniczna.put(firma3.nrTelefonu, firma3);
		
		for(Map.Entry<NrTelefoniczny, Wpis> iterator : ksiazkaTelefoniczna.entrySet())
		{
			iterator.getValue().opis();
		}
	}
}
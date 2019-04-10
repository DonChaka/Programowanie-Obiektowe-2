public class NrTelefoniczny implements Comparable<NrTelefoniczny>
{
	String nrKierunkowy;
	String nrTelefonu;
	String nrTelefonuCaly;
	
	public NrTelefoniczny(String kierunkowy, String telefon)
	{
		this.nrKierunkowy = kierunkowy;
		this.nrTelefonu = telefon;
		this.nrTelefonuCaly = kierunkowy + telefon;
	}
	
	@Override
	public int compareTo(NrTelefoniczny sprawdzany)
	{
		return nrTelefonuCaly.compareTo(sprawdzany.nrTelefonuCaly);
	}
}

abstract class Wpis
{
	public abstract void opis();
}

class Firma extends Wpis
{
	String nazwa;
	String adres;
	NrTelefoniczny nrTelefonu;
	
	public Firma(String nazwa, String adres, NrTelefoniczny nrTelefonu)
	{
		this.nazwa = nazwa;
		this.adres = adres;
		this.nrTelefonu = nrTelefonu;
	}
	
	public void opis()
	{
		System.out.println("Nazwa: " + nazwa + "\nAdres: " + adres + "\nNr telefonu: " + nrTelefonu.nrTelefonuCaly + "\n");
	}
}

class Osoba extends Wpis
{
	String imie;
	String nazwisko;
	String adres;
	NrTelefoniczny nrTelefonu;
	
	public Osoba(String imie, String nazwisko, String adres, NrTelefoniczny nrTelefonu)
	{
		this.imie = imie;
		this.nazwisko = nazwisko;
		this.adres = adres;
		this.nrTelefonu = nrTelefonu;
	}
	
	public void opis()
	{
		System.out.println("Nazwisko: " + nazwisko + "\nImie: " + imie + "\nAdres: " + adres + "\nNr Telefonu: " + nrTelefonu.nrTelefonuCaly + "\n");
	}
}
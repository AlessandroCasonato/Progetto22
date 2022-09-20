package bacheca;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Annuncio {
	
	//Caratteristiche annuncio
	private String nomeOggetto;
	private Utente utente;
	private int quantita;
	private int prezzo;
	private ArrayList<String> paroleChiave;
	private int ID;
	private char type;
	private LocalDate scadenza;
	

	public Annuncio(char T, String nomeOggetto, Utente u, int qnt, int prezzo, int ID, String scadenza, String... paroleChiave) throws FormatException{
		this.type = T;
		this.nomeOggetto = nomeOggetto;
		this.utente = u;
		this.quantita = qnt;
		this.prezzo = prezzo;
		this.ID = ID;		
		this.paroleChiave = new ArrayList<String>();
		this.scadenza = scadenza != "" ? LocalDate.parse(scadenza, DateTimeFormatter.ofPattern("dd-MM-yyyy")) : LocalDate.now().plusMonths(1);
		
		for (String parola : paroleChiave)
			this.paroleChiave.add(parola);
		}

	
	//Restituisce il tipo di annuncio (Compro o vendo?)
	public char getT() {
		return type;
	}
	
	//Restituisce lista di parole chiave
	public ArrayList<String> getParoleChiave() {
		return paroleChiave;
	}

	//Restituisce la quantità di un determinato articolo inserito negli annunci
	public int getQta() {
		return this.quantita;
	}
	
	//Restituisce codice identificativo annuncio
	public int getID() {
		return this.ID;
	}
	
	//Restituisce nome oggetto dell'annuncio
	public String getNome() {
		return this.nomeOggetto;
	}
		
	//Restituisce l'Email identificativa di chi ha inserito l'annuncio
	public String getEmailUtente() {
		return this.utente.getEmail();
	}
	
	//Restituisce la data di scadenza dell'annuncio
	public LocalDate getScadenza() {
		return this.scadenza;
	}
	
	public boolean setScadenza(String dataScadenza) {
		try
		{
			this.scadenza = LocalDate.parse(dataScadenza);
			return true;
		}
		catch (Exception ex)
		{
			return false;
		}
		
	}

	//Setta le parole chiave
	public void setParoleChiave(ArrayList<String> match) {
		this.paroleChiave = match;
	}
	
	//Restituisce stringa di parole chiave (esempio: Musica-Abbigliamento-Auto)
	public String listaParole() {
		
		String s = new String ();
			
		for(String tmp :this.paroleChiave)
			s+=tmp+"-";
		return s.substring(0, s.length()-1);
	}
	
	//Restituisce annuncio sotto forma di stringa
	public String toString() {
		return ( type+","+nomeOggetto+","+utente.getNome()+","+utente.getEmail()+","+quantita+","+prezzo+","+ID+"," +scadenza.toString()+ ","+this.listaParole());
	}

}

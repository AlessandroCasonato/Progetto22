package bacheca;
import java.util.ArrayList;

public class Utenti {
	
	//Creazione della lista di utenti
	private static ArrayList<Utente> utenti=new ArrayList<Utente>();	
	
	public static void crea() { 			
		utenti=new ArrayList<Utente>();
	}
	
	//Aggiunge un singolo utente alla lista
	public static void aggiungiUtente(Utente u) throws FormatException{		
		Utente p=new Utente(u.getNome(),u.getEmail());
		utenti.add(p);
	}

	//Rimuove un singolo utente dalla lista identificato tramite Email di registrazione
	public static void RimuoviUtente(String email)throws FormatException{

	for(int i=0;i<utenti.size();i++){
		Utente u= utenti.get(i);
		
		if(u.getEmail().equals(email)){
			utenti.remove(u);
			return;
		}
	}
	throw new FormatException("Utente non presente, impossibile rimuovere");
}
	
		
	//Restituisce una stringa contenente tutti gli identificativi (Email) degli utenti registrati
	public static String elencoUtenti(){
		
		String elenco = new String();
						
		for (Utente tmp : utenti)
			elenco+= tmp.getEmail() +"\n";
		return elenco;
	}
	
	
	//Controlla se un utente è presente nella lista tramite identificativo (Email)
	public static boolean contains(String email)	{
		
		for (Utente u : utenti)
			if(u.getEmail().equals(email)) {
				return true;
			}	
		return false;
	}
	
	
	
	//Restituisce il numero di utenti in lista
	public static int numEl() {
		return utenti.size();
	}
	
	//Restituisce un utente tramite ricerca per identificativo (Email)
	public static Utente getUtente(String email)	throws FormatException{
		
		for (Utente u : utenti)
			if (u.getEmail().equals(email)) {
				return u;
			}
		throw new FormatException("Utente non trovato");
		}
}


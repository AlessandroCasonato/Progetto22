package bacheca;
import java.util.ArrayList;
 

public class Utenti {
	private static ArrayList<Utente> utenti=new ArrayList<Utente>();	
	
	public static void crea() { 			
		utenti=new ArrayList<Utente>();
	}
	
	/*** Aggiunge un utente se la mail non è già presente nella lista utenti.
	 * @param utente da aggiungere
	 * @return true se l'utente è stato aggiunto con successo, false altrimenti
	 * @throws FormatException
	 */
	public static boolean aggiungiUtente(Utente u) throws FormatException{	
		if (containsUtente(u.getEmail()))
		{
			return false;
		}
		else
		{
			utenti.add(u);
			return true;
		}
	}

	//
	
	/*** Rimuove un singolo utente dalla lista identificato tramite Email di registrazione
	 * @param email dell'utente da rimuovere
	 * @throws FormatException
	 * @return true se l'utente è stato rimosso, false altrimenti
	 */
	public static boolean RimuoviUtente(String email)throws FormatException{
		return utenti.removeIf(u -> u.getEmail().equalsIgnoreCase(email));
	}
	
		
	/*** Restituisce una stringa contenente tutti gli identificativi (Email) degli utenti registrati
	 * @return l'elenco di utenti registrati
	 */
	public static String elencoUtenti(){
		
		String elenco = new String();
						
		for (Utente tmp : utenti)
			elenco+= tmp.getEmail() +"\n";
		return elenco;
	}
	
	/***Controlla se un utente è presente nella lista tramite identificativo (Email)
	 * @param email dell'utente da verificare
	 * @return
	 */
	public static boolean containsUtente(String email)	{
		
		for (Utente u : utenti)
		{
			if(u.getEmail().equals(email)) {
				return true;
			}	
		}
		return false;
	}
	
	
	/***Restituisce il numero di utenti in lista
	 * @return numero di elementi in lista
	 */
	public static int numEl() {
		return utenti.size();
	}
	
	/***Restituisce un utente tramite ricerca per identificativo (Email)
	 * @param email
	 * @return
	 * @throws FormatException
	 */
	public static Utente getUtente(String email)	throws FormatException{
		
		for (Utente u : utenti)
			if (u.getEmail().equals(email)) {
				return u;
			}
		throw new FormatException("Utente non trovato");
		}
}


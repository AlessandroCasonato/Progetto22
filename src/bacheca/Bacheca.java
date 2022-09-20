package bacheca;
import java.io.BufferedReader;
import java.io.Console;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


public class Bacheca {
	final int MAX_DIM=3;											//Numero massimo degli annunci	
	private ArrayList<Annuncio> annunci = new ArrayList<Annuncio>();	//Lista di oggetti annunci		    
	protected ArrayList<String> parole = new ArrayList<String>(List.of(
			"Abbigliamento", 
			"Alimentari", 
			"Altro", 
			"Arredamento", 
			"Auto", 
			"Bellezza", 
			"Casa", 
			"Elettronica",
			"Film", 
			"Giardinaggio", 
			"Giochi", 
			"Gioielli", 
			"Informatica", 
			"Libri",
			"Moto", 
			"Musica", 
			"Telefonia", 
			"Sport", 
			"Videogiochi" ));
	
	//Estendo interfaccia Iterator per scorrere e gestire lista di annunci
	public Iterator<Annuncio> iterator()
	{
		return new IteratoreBacheca();
	}
	
	private class IteratoreBacheca implements Iterator<Annuncio>
	{
		private int cursor;

		public IteratoreBacheca()
		{
			cursor = 0;
		}

		public boolean hasNext()
		{
			if (annunci.size()>MAX_DIM) return false;
			return (cursor<annunci.size());
		}
		public Annuncio next()
		{
			if (!this.hasNext())
				return null;
			return annunci.get(cursor++);
		}
	}

	
	public Boolean aggiungiAnnuncio(Annuncio annuncio)throws FormatException	{

		ArrayList<String> match  = new ArrayList<String>();
		
		if(!(Utenti.containsUtente(annuncio.getEmailUtente())))
			throw new FormatException("Utente non presente");
			
		
		for (Annuncio tmp : this.annunci){
			if (annuncio.getID() == tmp.getID())
				throw new FormatException("Annuncio gia presente");
		}
		for(String s: parole){
			if(annuncio.getParoleChiave().contains(s)){
				match.add(s);
			}
		}

		if(match.size()==0){
			throw new FormatException("Nessuna corrispondenza nelle parole chiave");
		}
		else{
			if (this.annunci.size()>=MAX_DIM) {
				throw new FormatException("Dimensione massima raggiunta");
			}
			annuncio.setParoleChiave(match);
			this.annunci.add(annuncio);
			return true;
		}
	}
		
	
	/***Restituisce la lista di parole chiave
	 * @return le parole chiave separate da trattino
	 */
	public String paroleChiaveString() 
	{
		String s = new String();
		
		for (String tmp : parole)
			s+=tmp+"-";
		return s;
	}

	/***Cerca annunci tramite l'identificativo dell'utente
	 * @param u
	 * @return l'insieme di annunci dell'utente
	 */
	public ArrayList<Annuncio> trovaAnnunci(Utente u) {
		ArrayList<Annuncio> risultato = new ArrayList<Annuncio>();
		
		for (Annuncio tmp : this.annunci)
			if (tmp.getEmailUtente().equals(u.getEmail()))
				risultato.add(tmp);
		return risultato;
	}
	
	/***Restituisce gi annunci dell'utente loggato
	 * @param u
	 * @return
	 */
	public String listaPropriAnnunci(Utente u){
		
		ArrayList <Annuncio> lista = trovaAnnunci(u);
		String s = new String();
		
		for (Annuncio tmp : lista){
			s+= tmp.toString()+("\n");
		}
		return s.substring(0, s.length()-1);
	}

	//Rimuove l'annuncio creato dall'utente stesso (un utente X non può eliminare annunci dell'utente Y)
	public void rimuoviAnnuncio(int identificatore, Utente u)throws FormatException {
		
		for(int i=0;i<annunci.size();i++){
			Annuncio c=annunci.get(i);
			
			if(c.getID()==identificatore && u.getEmail()==c.getEmailUtente()){
				annunci.remove(c);
				return;
			}
		}
		throw new FormatException("Nessun annuncio trovato");
	}
	
	//Restituisce l'annuncio cercato tramite codice identificativo
	public String visualizzaAnnuncio(int id_cercato)
	{
		for (Annuncio c : this.annunci) {
			if (c.getID() == id_cercato) {
				return c.toString();
			}
		}
		return "";

	}

	//Restituisce elenco totale degli annunci presenti
	public String elencoAnnunci()
	{
		String s = new String();
		
		for(Annuncio tmp : annunci)
			s += tmp.toString()+"\n";
		return s.substring(0, s.length()-1);
	}
	
	/***Restituisce l'identificatore di tutti gli annunci che combaciano con la parola chiave cercata dall'utente
	 * 
	 * @param s
	 * @return
	 */
	public ArrayList<Integer> intersezione(String ... s){
		
		ArrayList<Integer> array = new ArrayList<Integer>();
		
		for(Annuncio p: annunci){
			for(String c: p.getParoleChiave()){
				for(String z:s){
					if(!array.contains(p.getID())){
						if (c.equals(z))
							array.add(p.getID());
					}
				}
			}
		}

		return array;
	}
	
	//Leggi e salva annunci da file.txt
	public	void leggiBacheca(String nomeFile) throws FormatException, IOException{
		try
		{
			BufferedReader in = new BufferedReader(new FileReader(nomeFile));
			String linea = in.readLine();
			String[] dati = linea.split(",");
			
			while (linea != null) {
				dati = linea.split(",");
				
				if (dati.length >= 8) {

					char T = dati[0].charAt(0);
					String nomeOgg = dati[1].trim();
					String nomeU= dati[2].trim();
					String emailU= dati[3].trim();
					int  qnt = Integer.parseInt(dati[4]);
					int  prezzo= Integer.parseInt(dati[5]);
					int  ID = Integer.parseInt(dati[6]);
					String scadenza = dati[7].trim();
					String [] parCh = dati[8].split("-");
					Utente u = new Utente(nomeU,emailU);
					
					if (!(Utenti.containsUtente(u.getEmail())))
						Utenti.aggiungiUtente(u);
					this.aggiungiAnnuncio(new Annuncio(T,nomeOgg,u, qnt, prezzo, ID, scadenza, parCh));
					
				}
				linea = in.readLine();
			}
			in.close();	
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}
	}
	
	//Crea un file.txt nel quale salvare gli annunci inseriti da console
	public void scriviBacheca(String nomeFile) throws FileNotFoundException  {
			PrintWriter out = new PrintWriter(new File(nomeFile));
			out.printf(this.elencoAnnunci());
			out.close();
	}
	
	public void cleanupAnnunciScaduti() 
	{
		annunci.removeIf(a -> a.getScadenza().isBefore(LocalDate.now()));
	}
	
	
	//Restituisce numero degli annunci
	public int numEl(){
		return this.annunci.size();
	}

	/*** Aggiunge una o più parole chiave alla corrispondente lista
	 * @param paroleChiave
	 */
	public void aggiungiParoleChiave(ArrayList<String> paroleChiave) {
		for (String parolaChiave : paroleChiave)
		{
			if(!parole.contains(parolaChiave))
				parole.add(parolaChiave);
		}
	}

	/*** Rimuove una o più parole chiave alla corrispondente lista, rimuovendo
	 * ogni annuncio che la/le contiene
	 * @param paroleChiave
	 */
	public void rimozioneAnnunciPerParoleChiave(ArrayList<String> paroleChiave) {
		for (String parolaChiave : paroleChiave)
		{
			if (annunci.removeIf(a -> a.getParoleChiave().contains(parolaChiave.trim())))
				parole.removeIf(a -> a.equalsIgnoreCase(parolaChiave));
		}
	}
	
}

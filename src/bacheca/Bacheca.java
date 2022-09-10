package bacheca;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;


public class Bacheca {
	
	private ArrayList<Annunci> annunci;	//Lista di oggetti annunci
	final int MAX_DIM=3;		//Numero massimo degli annunci		

	//Lista di oggetti stringa contenente le parole chiave
	protected static ArrayList<String> parole = new ArrayList<String>();
	static{
		parole.add("Abbigliamento");
		parole.add("Alimentari");
		parole.add("Altro");
		parole.add("Arredamento");
		parole.add("Auto");
		parole.add("Bellezza");
		parole.add("Casa");
		parole.add("Elettronica");
		parole.add("Film");
		parole.add("Giardinaggio");
		parole.add("Giochi");
		parole.add("Gioielli");
		parole.add("Informatica");
		parole.add("Libri");
		parole.add("Moto");
		parole.add("Musica");
		parole.add("Telefonia");
		parole.add("Sport");
		parole.add("Videogiochi");

	}
	
	//Estendo interfaccia Iterator per scorrere e gestire lista di annunci
	public Iterator<Annunci> iterator()
	{
		return new IteratoreBacheca();
	}
	
	private class IteratoreBacheca implements Iterator<Annunci>
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
		public Annunci next()
		{
			if (!this.hasNext())
				return null;
			return annunci.get(cursor++);
		}

	}
	
	//Costruttore bacheca
	public Bacheca() 
	{
		this.annunci = new ArrayList<Annunci>();
	}
	
	
	public void aggiungiAnnuncio(Annunci annuncio)throws FormatException	{


		ArrayList<String> match  = new ArrayList<String>();
		
		if(!(Utenti.contains(annuncio.getNomeUtente())))
			throw new FormatException("Utente non presente");
			
		
		for (Annunci tmp : this.annunci){
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
		}
	}
		
	
	//Restituisce parole chiave
	public String paroleChiaveString() 
	{
		String s = new String();
		
		for (String tmp : parole)
			s+=tmp+"-";
		return s.substring(0,s.length()-1);
	}

	//Cerca annunci tramite l'identificativo dell'utente
	public ArrayList<Annunci> trovaAnnunci(Utente u) {
		ArrayList<Annunci> risultato = new ArrayList<Annunci>();
		
		for (Annunci tmp : this.annunci)
			if (tmp.getNomeUtente().equals(u.getEmail()))
				risultato.add(tmp);
		return risultato;
	}
	
	//Restituisce gi annunci dell'utente loggato
	public String listaPropriAnnunci(Utente u){
		
		ArrayList <Annunci> lista = trovaAnnunci(u);
		String s = new String();
		
		for (Annunci tmp : lista){
			s+= tmp.toString()+("\n");
		}
		return s.substring(0, s.length()-1);
	}

	//Rimuove l'annuncio creato dall'utente stesso (un utente X non può eliminare annunci dell'utente Y)
	public void rimuoviAnnuncio(int identificatore, Utente u)throws FormatException {
		
		for(int i=0;i<annunci.size();i++){
			Annunci c=annunci.get(i);
			
			if(c.getID()==identificatore && u.getEmail()==c.getNomeUtente()){
				annunci.remove(c);
				return;
			}
		}
		throw new FormatException("Nessun annuncio trovato");
	}
	
	//Restituisce l'annuncio cercato tramite codice identificativo
	public String visualizzaAnnuncio(int id_cercato)
	{
		for (Annunci c : this.annunci) {
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
		
		for(Annunci tmp : annunci)
			s += tmp.toString()+"\n";
		return s.substring(0, s.length()-1);
	}
	
	//Restituisce l'identificatore di tutti gli annunci che combaciano con la parola chiave cercata dall'utente
	public ArrayList<Integer> intersezione(String ... s){
		
		ArrayList<Integer> array = new ArrayList<Integer>();
		
		for(Annunci p:this.annunci){
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
		
			BufferedReader in = new BufferedReader(new FileReader(nomeFile));
			String linea = in.readLine();
			String[] dati = linea.split(",");
			
			while (linea != null) {
				dati = linea.split(",");
				
				if (dati.length >= 7) {

					char T = dati[0].charAt(0);
					String nomeOgg = dati[1].trim();
					String nomeU= dati[2].trim();
					String emailU= dati[3].trim();
					int  qnt = Integer.parseInt(dati[4]);
					int  prezzo= Integer.parseInt(dati[5]);
					int  ID = Integer.parseInt(dati[6]);
					String [] parCh = dati[7].split("-");
					Utente u = new Utente(nomeU,emailU);
					
					if (!(Utenti.contains(u.getNome())))
						Utenti.aggiungiUtente(u);
					this.aggiungiAnnuncio(new Annunci(T,nomeOgg,u, qnt, prezzo,ID, parCh));
					
				}
				linea = in.readLine();
			}
			in.close();
		
	}
	
	//Crea un file.txt nel quale salvare gli annunci inseriti da console
	public void scriviBacheca(String nomeFile) throws FileNotFoundException  {

			PrintWriter out = new PrintWriter(new File(nomeFile));
			out.printf(this.elencoAnnunci());
			out.close();
		
	}
	
	//Restituisce numero degli annunci
	public int numEl(){
		return this.annunci.size();
	}
	
}

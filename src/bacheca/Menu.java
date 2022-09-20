package bacheca;
import jbook.util.Input;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class Menu {
	
	public static void main(String[] args) throws FormatException, IOException {
		
		Bacheca bach = new Bacheca();	//Creo oggetto di tipo bacheca
		int risp=0;						//Variabile per monitorare il tipo di risposta utente
		
		//Inizio a creare il menu destinato all'utente finale per creare e gestire utenti e bacheca
		while(risp!=3){			
			try{
				switch(risp)
				{
				case 1:
					modUtente();
					risp=0;
					break;
				case 2:
					String email = Input.readString("Inserisci la tua email utente: ");
					
					if (Utenti.containsUtente(email))
							modBacheca(bach, Utenti.getUtente(email));
					else
						System.out.println("Inserisci una email valida");
					risp=0;
					break;
				case 3:
					break;
				default: 
					risp = Input.readInt("====================\nScegli:\n1)Gestione Utenti\n2)Gestione Bacheca\n3)ESCI\n");
				}
			}
			catch (NumberFormatException e){
				System.out.println("\nDevi inserire un valore intero\n---------------");
			}
			catch (FormatException e){
				System.out.println("\n"+e.getMessage()+"\n");
				risp = 0;
			}	
		}
		return;
	}
	
	
	
	//Metodo per gestire la modalità bacheca
	private static void modBacheca(Bacheca bach, Utente u) throws NumberFormatException,FormatException, IOException
	{		
		int modb=0;
		while((modb>0)||(modb<12)) {
			try {
				System.out.println("====================\nGestione Bacheca\n");
				System.out.println("1) Ottieni l'elenco delle parole chiave\n"
						+ "2) Ottieni tutti gli ID degli annunci che appartengono a determinate parole chiave\n"
						+ "3) Visualizza tutti i tuoi annunci\n" 
						+ "4) Visualizza il contenuto di un annuncio\n"
						+ "5) Inserisci un annuncio nella bacheca\n"
						+ "6) Rimuovi un annuncio\n" 
						+ "7) Visualizza tutti gli annunci in bacheca\n"
						+ "8) Leggi la bacheca da file\n"
						+ "9) Stampa su file\n"
						+ "10) Esegui cleanup annunci scaduti\n"
						+ "11) Rimuovi una o più parole chiave e i relativi annunci\n"
						+ "12) Aggiungi una o più parole chiave\n"
						+ "13) Logout\n");
				
				modb = Input.readInt();
				switch(modb) {
				case 1:
					System.out.println(bach.paroleChiaveString());
					break;
				case 2:
					String tmp = Input.readString("Scrivi un sottoinsieme di parole chiave di cui vuoi ottenere gli ID: (Separare le parole chiave con un '-' e senza spazi):\n");
					String [] listakeys = tmp.split("-");
					ArrayList<Integer> listID = new ArrayList<Integer>();
					listID= bach.intersezione(listakeys);
					System.out.println("\nLista degli id:\n"+listID.toString());
					break;
				case 3:
					System.out.println("\nPropri annunci presenti in bacheca:\n"+bach.listaPropriAnnunci(u));
					break;
				case 4:
					int id = Input.readInt("Inserisci l'id dell'annuncio da visualizzare: ");
					System.out.println("Annuncio: "+bach.visualizzaAnnuncio(id));
					break;
				case 5:
					int count = 0 ;
					String[] data = new String[7];
					data[count++]=Input.readString("Inserisci il tipo di annuncio(V/C): ");
					data[count++]=Input.readString("Inserisci il nome dell'oggetto: ");
					data[count++]=Input.readString("Inserisci la quantita': ");
					data[count++]=Input.readString("Inserisci il prezzo: ");
					data[count++]=Input.readString("Inserisci l'identificatore: ");
					data[count++]=Input.readString("Inserisci la data di scadenza nel formato dd-mm-yyyy (premere invio senza digitare nulla per scadenza tra un mese):");
					data[count++]=Input.readString("Inserisci delle parole chiave separate da un '-' senza spazi in mezzo: ");

					String[] listaChiavi = data[6].split("-");
					Boolean res = bach.aggiungiAnnuncio(new Annuncio(data[0].charAt(0),data[1],u,Integer.parseInt(data[2]),Integer.parseInt(data[3]),Integer.parseInt(data[4]), data[5], listaChiavi));
					if (res && data[0].charAt(0) == 'C')
					{
						listID= bach.intersezione(listaChiavi);
						System.out.println("\nLista degli id di annunci di acquisto con stesse parole chiave:\n"+listID.toString());
					}
					break;
				case 6:	
					int id1= Input.readInt("Inserisci l'id dell'annuncio da eliminare: ");
					bach.rimuoviAnnuncio(id1, u);
					System.out.println(bach.elencoAnnunci());
					break;
				case 7:
					System.out.println("ELENCO ANNUNCI:\n"+bach.elencoAnnunci());
					break;
				case 8:
					String nomeFile= Input.readString("Inserisci il nome del file da cui importare la bacheca: ");
					bach.leggiBacheca(nomeFile);
					System.out.println("ELENCO ANNUNCI:\n"+bach.elencoAnnunci());
					break;
				case 9:
					nomeFile= Input.readString("Inserisci il nome del file su cui scrivere la bacheca: ");
					bach.scriviBacheca(nomeFile);
					System.out.println("Bacheca scritta con successo!\n");
					break;
				case 10:
					bach.cleanupAnnunciScaduti();
					System.out.println("ELENCO ANNUNCI DOPO CLEANUP:\n"+bach.elencoAnnunci());
					break;
				case 11:
					ArrayList<String> paroleChiave = new ArrayList<String>();
					String inputParola = "";
					do {
						inputParola = Input.readString("Inserisci una parola chiave da cercare, o premi invio senza inserire nulla per proseguire: ");
						paroleChiave.add(inputParola);
					} while (inputParola != "");
					bach.rimozioneAnnunciPerParoleChiave(paroleChiave);
					System.out.println("ELENCO ANNUNCI DOPO RIMOZIONE PER PAROLA CHIAVE:\n"+bach.elencoAnnunci());
					break;
				case 12:
					paroleChiave = new ArrayList<String>();
					inputParola = "";
					do {
						inputParola = Input.readString("Inserisci una parola chiave da cercare, o premi invio senza inserire nulla per proseguire: ");
						paroleChiave.add(inputParola);
					} while (inputParola != "");
					bach.aggiungiParoleChiave(paroleChiave);
					System.out.println("ELENCO PAROLE CHIAVE DOPO AGGIUNTA:\n"+bach.paroleChiaveString());
					break;
				default:
					break;
				}
			}
			catch (FormatException e)
			{
				System.out.println("\n"+e.getMessage()+"!!!\n---------------");
			}
			catch (StringIndexOutOfBoundsException e)
			{
				System.out.println("\nNon sono presenti annunci!!!\n---------------");
			}
			catch (NumberFormatException e)
			{
				System.out.println("\nDevi inserire un valore intero!!!\n---------------");
			}
			catch(FileNotFoundException e)
			{
				System.out.println("\nFile non trovato o formato file non adeguato!");
			}
		}	
	}
	
	
	//Metodo per gestire la modalità utente
	private static void modUtente ()
	{
		int modu=0;
		while((modu!=2)||(modu!=1)){
			try{
				System.out.println("Gestione utenti\n");
				System.out.println("1) Aggiungi utente");
				System.out.println("2) Rimuovi utente");
				System.out.println("3) TORNA INDIETRO");
				
				modu = Input.readInt();
				
				if(modu==1){
							String nome = Input.readString("Inserisci il nome utente: ");
							String email = Input.readString("Inserisci l'email dell'utente: ");
							Utente u = new Utente(nome,email);
							Utenti.aggiungiUtente(u);					
				}	
				else if(modu==2){
	
					if (Utenti.numEl()==0) System.out.println("Non ci sono utenti in bacheca\n");
					else{
							System.out.println("Lista degli Utenti:\n"+Utenti.elencoUtenti());
							String email= Input.readString("Inserisci l'email dell'utente da rimuovere: ");
							Utenti.RimuoviUtente(email);
						}	
				}
				else if (modu==3)
					return;
							
			}
			catch (NumberFormatException e)
			{
				System.out.println("\nInserisci un valore intero\n---------------");
			}
			catch (FormatException e)
			{
				System.out.println("\n"+e.getMessage()+"\n---------------");
			}
			}
	}
}
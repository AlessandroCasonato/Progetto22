package bacheca;

public class Utente {

	private final String nome;	//Nome utente
	private String email;		//E-mail utente
	
	//Costruttore Utente
	public Utente(String nome, String email){  
		this.nome = nome;
		this.email = email;
	}
	
	//Restituisce nome utente
	public String getNome(){		
		return this.nome;
	}
	
	//Restituisce e-mail
	public String getEmail(){	
		return this.email;
	}
}

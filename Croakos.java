
import java.io.Serializable;
import java.util.ArrayList;

public class Croakos implements Serializable{

	private static final long serialVersionUID = 8040518096487622839L;
	
	String nom;
	String mdp;
	ArrayList<Croakos> followers = new ArrayList<Croakos>();
	ArrayList<Croakos> following = new ArrayList<Croakos>();
	

	@Override
	public String toString() {
		return "Croakos [nom=" + nom + ", mdp=" + mdp + "]";
	}

	public Croakos(String nom, String mdp) {
		super();
		this.nom = nom;
		this.mdp = mdp;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getMdp() {
		return mdp;
	}

	public void setMdp(String mdp) {
		this.mdp = mdp;
	}
	
	
	
}

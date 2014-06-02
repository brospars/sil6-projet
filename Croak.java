
import java.io.Serializable;
import java.util.Date;

public class Croak implements Serializable{
	
	@Override
	public String toString() {
		return "Croak [date=" + date + ", message=" + message + ", auteur="
				+ auteur + "]";
	}


	private static final long serialVersionUID = -2038300624034387060L;
	
	Date date;
	String message;
	Croakos auteur;
	
	
	public Croak(Date date, String message, Croakos auteur) {
		super();
		this.date = date;
		this.message = message;
		this.auteur = auteur;
	}


	public Date getDate() {
		return date;
	}


	public void setDate(Date date) {
		this.date = date;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public Croakos getAuteur() {
		return auteur;
	}


	public void setAuteur(Croakos auteur) {
		this.auteur = auteur;
	}
	
	
}

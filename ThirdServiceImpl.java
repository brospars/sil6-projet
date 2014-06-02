
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.rmi.RemoteException;
import java.util.ArrayList;


public class ThirdServiceImpl extends Thread implements ThirdService {
	
	public ThirdServiceImpl() throws RemoteException {
		super();
	}
	
	/**
	 * Sauvegarde un utilisateur
	 * @param Croakos
	 */
	public void saveUser(Croakos user) throws RemoteException {
		// Write to disk with FileOutputStream
		FileOutputStream f_out;
		try {
			f_out = new FileOutputStream("src/sil6-projet/"+user.getNom());
			try {
				ObjectOutputStream obj_out = new ObjectOutputStream (f_out);
				obj_out.writeObject (user);
				obj_out.flush();
				obj_out.close();
			} catch (IOException e) { e.printStackTrace(); }
		} catch (FileNotFoundException e) { e.printStackTrace();	}
	}
	
	/**
	 * Recupere un croakos (un utilisateur)
	 * @return 
	 */
	public Croakos getUser(String name) throws RemoteException {
		
		Croakos user;
		
		try(
		      InputStream file = new FileInputStream("src/sil6-projet/"+name);
		      InputStream buffer = new BufferedInputStream(file);
		      ObjectInput input = new ObjectInputStream (buffer);
		){
			//deserialize un utilisateur
			user = (Croakos)input.readObject();
			return user;
		}
		catch(IOException | ClassNotFoundException e){
			e.printStackTrace();
		}
		return null;	
	}

	/**
	 * Sauvegarde de la liste des Croaks dans le fichier
	 * @param listeCroak
	 */
	public void saveAllCroaks(ArrayList<Croak> listeCroak) throws RemoteException {
			
		// Write to disk with FileOutputStream
		FileOutputStream f_out;
		try {
			f_out = new FileOutputStream("src/sil6-projet/croaks.txt");
			try {
				ObjectOutputStream obj_out = new ObjectOutputStream (f_out);
				obj_out.writeObject (listeCroak);
				obj_out.flush();
				obj_out.close();
			} catch (IOException e) { e.printStackTrace(); }
		} catch (FileNotFoundException e) { e.printStackTrace();	}
		
	}
	
	
	/**
	 * Lecture de la liste des Croaks (tweet) dans le fichier
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<Croak> getAllCroaks() throws RemoteException {
		
		ArrayList<Croak> listeCroak = new ArrayList<Croak>();
		
		try(
		      InputStream file = new FileInputStream("src/sil6-projet/croaks.txt");
		      InputStream buffer = new BufferedInputStream(file);
		      ObjectInput input = new ObjectInputStream (buffer);
		){
			  //deserialize the croak list
			  listeCroak = (ArrayList<Croak>)input.readObject();
		}
		catch(IOException | ClassNotFoundException e){
			e.printStackTrace();
		}
		
		return listeCroak;
		
	}
	
}

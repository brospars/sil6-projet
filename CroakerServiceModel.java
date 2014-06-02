package projet;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;

public class CroakerServiceModel {
	
	public static void main(String [] args){
		
		Croakos croakos = new Croakos("nom","mdp");
		ArrayList<Croak> listeCroak = new ArrayList<Croak>();
		ArrayList<Croak> listeCroakRetrieve = new ArrayList<Croak>();
		
		for(int i=0;i<10;i++){
			Croak croak = new Croak(new Date(),"i",croakos);
			listeCroak.add(croak);
			System.out.println("Nouveau "+croak.toString());
		}
		
		saveAllCroaks(listeCroak);
		
		listeCroakRetrieve = getAllCroaks();
		
		for(int i=0;i<listeCroakRetrieve.size();i++){
			System.out.println("Lecture du "+listeCroakRetrieve.get(i).toString());
		}
	}
	
	public static void saveAllCroaks(ArrayList<Croak> listeCroak){
			
		// Write to disk with FileOutputStream
		FileOutputStream f_out;
		try {
			f_out = new FileOutputStream("src/projet/croaks.txt");
			try {
				ObjectOutputStream obj_out = new ObjectOutputStream (f_out);
				obj_out.writeObject (listeCroak);
				obj_out.flush();
				obj_out.close();
			} catch (IOException e) { e.printStackTrace(); }
		} catch (FileNotFoundException e) { e.printStackTrace();	}
		
	}
	
	@SuppressWarnings("unchecked")
	public static ArrayList<Croak> getAllCroaks(){
		
		ArrayList<Croak> listeCroak = new ArrayList<Croak>();
		
		try(
		      InputStream file = new FileInputStream("src/projet/croaks.txt");
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

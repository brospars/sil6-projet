package projet;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;

public class CroakerServiceModel {
	
	public static void main(String [] args){
		
		Croakos croakos = new Croakos("nom","mdp");
		ArrayList<Croak> listeCroak = new ArrayList<Croak>();
		
		for(int i=0;i<10;i++){
			Croak croak = new Croak(new Date(),"i",croakos);
			listeCroak.add(croak);
		}
		
		saveAllCroaks(listeCroak);
	}
	
	public static void saveAllCroaks(ArrayList<Croak> listeCroak){
			
		// Write to disk with FileOutputStream
		FileOutputStream f_out;
		try {
			f_out = new FileOutputStream("croaks.txt");
			try {
				
				ObjectOutputStream obj_out = new ObjectOutputStream (f_out);
				obj_out.writeObject (listeCroak);
				obj_out.close();
			} catch (IOException e) { e.printStackTrace(); }
		} catch (FileNotFoundException e) { e.printStackTrace();	}
		
	}
	
	public ArrayList<Croak> getAllCroaks(){
		
		ArrayList<Croak> listeCroak = new ArrayList<Croak>();
		
		
		
		return listeCroak;
		
	}
	
}

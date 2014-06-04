/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sil6.model;


import com.sil6.v1.ressources.Croak;
import com.sil6.v1.ressources.Croakos;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;


public class ThirdServiceImpl extends Thread implements ThirdService {
	
	public static final String PATH_USER = "data/users/";
	public static final String PATH_CROAKS = "data/croaks.txt";
	
	public ThirdServiceImpl() throws RemoteException {
		super();
	}
        
        public static void main(String [] args) throws Exception {
		  Registry registry;
		
		  ThirdServiceImpl thirdService = new ThirdServiceImpl();
		  
		  ThirdService thirdServiceStub = (ThirdService) UnicastRemoteObject.exportObject(thirdService, 0);
		  
		  registry = LocateRegistry.createRegistry(2000);
		
		  registry.bind("ThirdService", thirdService);
                  
		  System.out.println("Third service started");
		  System.in.read();
	 }
	
	/**
	 * Sauvegarde un utilisateur
	 * @param Croakos
	 */
	public void saveUser(Croakos user) throws RemoteException {
		// Write to disk with FileOutputStream
		FileOutputStream f_out;
		try {
			f_out = new FileOutputStream(PATH_USER+user.getNom());
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
                
                
                File fichier = new File(PATH_USER+name);
		if(fichier.exists()){
                    try(
                          InputStream file = new FileInputStream(fichier);
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
                }
		return null;	
	}
	
	/**
	 * Retourne la liste des utilisateurs inscrits
	 */
	public ArrayList<Croakos> getAllUsers() throws RemoteException{
		ArrayList<Croakos> users = new ArrayList<Croakos>();
		
		File repertoire = new File(PATH_USER);
		if(repertoire.exists() && repertoire.list() != null){
			String [] listFichiers = repertoire.list();
			
			for(int i=0;i<listFichiers.length;i++){
				users.add(getUser(listFichiers[i]));
			}
		}
		return users;
	}
	

	/**
	 * Sauvegarde de la liste des Croaks dans le fichier
	 * @param listeCroak
	 */
	public void saveAllCroaks(ArrayList<Croak> listeCroak) throws RemoteException {
			
		// Write to disk with FileOutputStream
		FileOutputStream f_out;
		try {
			f_out = new FileOutputStream(PATH_CROAKS);
			try {
				ObjectOutputStream obj_out = new ObjectOutputStream (f_out);
				obj_out.writeObject (listeCroak);
				obj_out.flush();
				obj_out.close();
			} catch (IOException e) { e.printStackTrace(); }
		} catch (FileNotFoundException e) { e.printStackTrace();	}
		
	}
        
        public void saveCroak(Croak croak) throws RemoteException {
            
        }
	
	
	/**
	 * Lecture de la liste des Croaks (tweet) dans le fichier
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<Croak> getAllCroaks() throws RemoteException {
		
		ArrayList<Croak> listeCroak = new ArrayList<Croak>();
		
		try(
		      InputStream file = new FileInputStream(PATH_CROAKS);
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

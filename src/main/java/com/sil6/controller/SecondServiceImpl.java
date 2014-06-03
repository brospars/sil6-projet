/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sil6.controller;

import com.sil6.model.ThirdService;
import com.sil6.v1.ressources.Croakos;
import java.rmi.Naming;
import java.rmi.Remote;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Path;

// Set the path, version 1 of API
@Path("/v1/test")
public class SecondServiceImpl extends Thread implements SecondService, Remote{
	
	private ArrayList<Croakos> liste_croakos;
	
	public SecondServiceImpl(){
		liste_croakos = new ArrayList<Croakos>();
	}
	
	public static void main(String args[]) throws Exception {
		ThirdService thirdService;
		// Ecoute le port 2000 pour communiquer avec le 3em Tiers
		thirdService = (ThirdService) Naming.lookup("rmi://localhost:2000/ThirdService");
		
		SecondServiceImpl secondService = new SecondServiceImpl();
		
		//Creer des utilisateurs test et le sauvegarde
		Croakos croakos = new Croakos("Benoit","123456");
		thirdService.saveUser(croakos);
		croakos = new Croakos("Boris","123456");
		thirdService.saveUser(croakos);
		
		//Recupération des utilisateurs après en avoir créé
		secondService.setListeCroakos(thirdService.getAllUsers());
		secondService.printUsers();
                
                System.out.println("Second service started");
		System.in.read();
	}

	public ArrayList<Croakos> getListeCroakos() {
		return liste_croakos;
	}

	public void setListeCroakos(ArrayList<Croakos> liste_croakos) {
		this.liste_croakos = liste_croakos;
	}
	
	public void printUsers(){
		for(Croakos i : this.liste_croakos){
			System.out.println(i.getNom());
		}
	}
        
        @Override
        public List<Croakos> getCroakos() {
            List<Croakos> croakos = getListeCroakos();

            return croakos;
        }
}


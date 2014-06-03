/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sil6.controller;

import com.sil6.model.ThirdService;
import com.sil6.v1.ressources.Croakos;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Path;

// Set the path, version 1 of API
@Path("/v1/croakos")
public class CroakosRessourcesImpl implements CroakosRessources{
	
	private ArrayList<Croakos> liste_croakos;

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
            ThirdService thirdService = null;
            try {
                // Ecoute le port 2000 pour communiquer avec le 3em Tiers
                thirdService = (ThirdService) Naming.lookup("rmi://localhost:2000/ThirdService");
            } catch (NotBoundException | MalformedURLException | RemoteException ex) {
                Logger.getLogger(CroakosRessourcesImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
            if(thirdService != null){
                try {
                    this.setListeCroakos(thirdService.getAllUsers());
                    this.printUsers();
                } catch (RemoteException ex) {
                    Logger.getLogger(CroakosRessourcesImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            List<Croakos> croakos = getListeCroakos();
            return croakos;
        }
}



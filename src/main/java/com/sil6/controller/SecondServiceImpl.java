/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sil6.controller;

import com.sil6.model.ThirdService;
import com.sil6.v1.ressources.MultiCroakos;
import com.sil6.v1.ressources.Croak;
import com.sil6.v1.ressources.Croakos;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBElement;

// Set the path, version 1 of API
@Path("/")
public class SecondServiceImpl extends Thread implements SecondService, Remote{
	
        private ThirdService thirdService = null;
        

        /* Constructor : connexion with RMI to the third tiers */
        public SecondServiceImpl() {
            try {
                // Ecoute le port 2000 pour communiquer avec le 3em Tiers
                thirdService = (ThirdService) Naming.lookup("rmi://localhost:2000/ThirdService");
            } catch (NotBoundException | MalformedURLException | RemoteException ex) {
                Logger.getLogger(SecondServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        @Override
        public MultiCroakos getCroakos() {
            
            ArrayList<Croakos> liste_croakos = null;
            
            MultiCroakos allCroakos = new MultiCroakos();
            
            if(thirdService != null){
                try {
                    liste_croakos = thirdService.getAllUsers();
                } catch (RemoteException ex) {
                    Logger.getLogger(SecondServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            allCroakos.liste = liste_croakos;
            
            return allCroakos;
        }
        

        @Override
        //@GET
        //@Path("getUser/{name}")
        //@Produces({MediaType.APPLICATION_JSON})
        public Croakos getUser(@PathParam("name") String name) {
            Croakos user = null;
            System.out.println(name);
            if(thirdService != null){
                try {
                    user = thirdService.getUser(name);
                } catch (RemoteException ex) {
                    Logger.getLogger(SecondServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            return user;
        }
        
        @Override
        @GET
        @Path("connexion/{name}/{mdp}")
        @Produces({MediaType.APPLICATION_JSON})
        public Croakos connexion(@PathParam("name") String name, @PathParam("mdp") String mdp) {
            Croakos user = null;
            System.out.println(name+" "+ mdp);
            if(thirdService != null){
                try {
                    user = thirdService.getUser(name);
                    if(!user.getMdp().equals(mdp) ){
                        user = null;
                    }
                } catch (RemoteException ex) {
                    Logger.getLogger(SecondServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if(user == null){ //Si l'utilisateur n'existe pas on lui renvoit un Croakos vide (retour à la connexion)
                return new Croakos();
            }
            return user;
        }

        @Override
        public boolean inscription(String name, String mdp) {
            boolean bool = false;
            if(this.getUser(name) == null){
                Croakos user = new Croakos(name, mdp);
                if(thirdService != null){
                    try {
                        thirdService.saveUser(user);
                        bool=true;
                    } catch (RemoteException ex) {
                        Logger.getLogger(SecondServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            else{
                bool = false; //Si l'utilisateur existe déjà
            }
            return bool;
        }

        @Override
        public String abonnement(JAXBElement<MultiCroakos> listAbo) {
            MultiCroakos aboList = listAbo.getValue();
            
            Croakos follower = aboList.liste.get(0);
            Croakos following = aboList.liste.get(1);
            
            if(follower != null && following != null){
                List<Croakos> followings = follower.getFollowing();
                List<Croakos> followers = following.getFollowers();

                followings.add(following);
                followers.add(follower);

                follower.setFollowing(followings);
                following.setFollowers(followers);
                
                if(thirdService != null){
                    try {
                        thirdService.saveUser(follower);
                        thirdService.saveUser(following);
                       
                    } catch (RemoteException ex) {
                        Logger.getLogger(SecondServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                return "true";
            }else{
                return "false";
            }
        }

        @Override
        public List<Croak> getCroaks(String name) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public boolean postCroak(JAXBElement<Croak> c) {
            Croak croak = c.getValue();
            
            return false;
        }
}


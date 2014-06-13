/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sil6.controller;

import com.sil6.model.ThirdService;
import com.sil6.v1.ressources.MultiCroakos;
import com.sil6.v1.ressources.Croak;
import com.sil6.v1.ressources.CroakList;
import com.sil6.v1.ressources.Croakos;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
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
public class SecondServiceImpl extends Thread implements SecondService, Remote {

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

        if (thirdService != null) {
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
    @GET
    @Path("getUser/{name}")
    @Produces({MediaType.APPLICATION_JSON})
    public Croakos getUser(@PathParam("name") String name) {
        Croakos user = null;
        System.out.println(name);
        if (thirdService != null) {
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
        System.out.println(name + " " + mdp);
        if (thirdService != null) {
            try {
                user = thirdService.getUser(name);
                if (user != null && !user.getMdp().equals(mdp)) {
                    user = null;
                }
            } catch (RemoteException ex) {
                Logger.getLogger(SecondServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (user == null) { //Si l'utilisateur n'existe pas on lui renvoit un Croakos vide (retour à la connexion)
            user = new Croakos();
        }
        return user;
    }

    @Override
    public String inscription(JAXBElement<Croakos> croakos) {
        String res = "false";
        Croakos new_croakos = croakos.getValue();
        try {
            if (thirdService.getUser(new_croakos.getNom()) == null) {
                if (thirdService != null) {
                    try {
                        thirdService.saveUser(new_croakos);
                        res = "true";
                    } catch (RemoteException ex) {
                        Logger.getLogger(SecondServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } else {
                res = "false"; //Si l'utilisateur existe déjà
            }
        } catch (RemoteException ex) {
            Logger.getLogger(SecondServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return res;
    }

    @Override
    public String abonnement(JAXBElement<MultiCroakos> listAbo) {
        MultiCroakos aboList = listAbo.getValue();

        Croakos follower = aboList.liste.get(0);
        Croakos following = aboList.liste.get(1);

        if (follower != null && following != null) {
            ArrayList<String> followings = follower.getFollowing();
            ArrayList<String> followers = following.getFollowers();

            followings.add(following.getNom());
            followers.add(follower.getNom());

            follower.setFollowing(followings);
            following.setFollowers(followers);

            if (thirdService != null) {
                try {
                    thirdService.saveUser(follower);
                    thirdService.saveUser(following);

                } catch (RemoteException ex) {
                    Logger.getLogger(SecondServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            return "true";
        } else {
            return "false";
        }
    }

    @Override
    public String desabonnement(JAXBElement<MultiCroakos> listAbo) {
        MultiCroakos aboList = listAbo.getValue();

        Croakos follower = aboList.liste.get(0);
        Croakos unfollowing = aboList.liste.get(1);

        if (follower != null && unfollowing != null) {
            ArrayList<String> followings = follower.getFollowing();
            ArrayList<String> followers = unfollowing.getFollowers();

            followings.remove(unfollowing.getNom());
            followers.remove(follower.getNom());

            follower.setFollowing(followings);
            unfollowing.setFollowers(followers);

            if (thirdService != null) {
                try {
                    thirdService.saveUser(follower);
                    thirdService.saveUser(unfollowing);
                } catch (RemoteException ex) {
                    Logger.getLogger(SecondServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            return "true";
        } else {
            return "false";
        }
    }

    @Override
    public CroakList getCroaks(String name) {
        Croakos user = null;
        CroakList timeLine = new CroakList();
        if (thirdService != null) {
            try {
                ArrayList<Croak> allCroaks = thirdService.getAllCroaks();
                user = thirdService.getUser(name);
                ArrayList<String> following = user.getFollowing();

                for(int i=0;i<allCroaks.size();i++){
                    //Si le tweet fait parti de ses following alors l'ajouter à la liste de sa timeline
                    String nameCroakos = allCroaks.get(i).getAuteur().getNom();
                    if((following.contains(nameCroakos)) || (name.equals(nameCroakos)) ){ 
                        timeLine.croakList.add( allCroaks.get(i) );
                    }
                }

            } catch (RemoteException ex) {
                Logger.getLogger(SecondServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return timeLine;
    }

    @Override
    public String postCroak(JAXBElement<Croak> c) {
        Croak croak = c.getValue();
        String retour = "false";
        if(thirdService != null){
            try {
                ArrayList<Croak> allCroaks = thirdService.getAllCroaks();
                allCroaks.add(croak);
                System.out.println(croak.getMessage());
                thirdService.saveAllCroaks(allCroaks);
                retour = "true";

            } catch (RemoteException ex) {
                Logger.getLogger(SecondServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return retour;
    }
}

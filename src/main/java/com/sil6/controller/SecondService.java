/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sil6.controller;

import com.sil6.v1.ressources.Croakos;
import java.rmi.Remote;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

public interface SecondService extends Remote{
    
    
    /**
     * Retourne la liste d'utilisateur (croakos)
     * @return 
     */
    @GET
    @Path("users")
    @Produces({MediaType.APPLICATION_JSON})
    List<Croakos> getCroakos();
    
    /**
     * Retourne l'utilisateur de nom "name"
     * @param name
     * @return 
     */
    @GET
    @Path("getUser/{name}")
    @Produces({MediaType.APPLICATION_JSON})
    public Croakos getUser(@PathParam("name") String name);
    
    //Fonction de connexion (verification que le client existe)
    //Les login et mdp sont passé en parametre
    
    //Inscription (verification que le client n'existe pas deja)
    //Login et mdp en paremetre
    
    //Poste d'un tweet
    //Message en parametre
    
    //Abonnement a un utilisateur (mettre à jour les abonnements etc.. + verifie l'existence)
    //Nom de l'utilisateur où s'abonner en param
    
    //Recuperation de la liste des tweets (les tweets de ses abonnement)
    //Nom d'utilisateur courrant en parametre
    
}

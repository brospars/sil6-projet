/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sil6.controller;

import com.sil6.v1.ressources.Croak;
import com.sil6.v1.ressources.Croakos;
import java.rmi.Remote;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBElement;

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
    @GET
    @Path("connexion/{name}/{mdp}")
    @Produces({MediaType.APPLICATION_JSON})
    public Croakos connexion(@PathParam("name") String name, @PathParam("mdp") String mdp);
    
    //Inscription (verification que le client n'existe pas deja)
    //Login et mdp en parametre
    @PUT
    @Path("inscription/{name}/{mdp}")
    @Consumes("plain/text")
    public boolean inscription(@PathParam("name") String name, @PathParam("mdp") String mdp);
    
    /**
     * Poste d'un tweet
     * Message en parametre
     * @Retour boolean
     */
    @PUT
    @Path("postCroak/{croak}")
    @Consumes(MediaType.APPLICATION_XML)
    public boolean postCroak (@PathParam("croak") JAXBElement<Croak> croak);
    
    
    /**
     *Abonnement a un utilisateur (mettre à jour les abonnements etc.. + verifie l'existence)
     *Nom de l'utilisateur où s'abonner en param + nom de celui qui s'abonne
    */
    @PUT
    @Path("abonnement/{nameUser}/{nameAbo}")
    @Consumes("text/plain")
    public boolean abonnement(@PathParam("nameUser") String nameUser, @PathParam("nameAbo") String nameAbo);
    
    //Recuperation de la liste des tweets (les tweets de ses abonnement)
    //Nom d'utilisateur courrant en parametre
    @GET
    @Path("getCroaks/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Croak> getCroaks(@PathParam("name") String name);
}

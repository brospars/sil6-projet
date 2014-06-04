/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sil6.controller;

import com.sil6.v1.ressources.MultiCroakos;
import com.sil6.v1.ressources.Croak;
import com.sil6.v1.ressources.CroakList;
import com.sil6.v1.ressources.Croakos;
import java.rmi.Remote;
import java.sql.Array;
import java.util.ArrayList;
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
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    MultiCroakos getCroakos();
    
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
    @Path("inscription")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces("text/plain")  
    public String inscription(JAXBElement<Croakos> croakos);
    
    /**
     * Poste d'un tweet
     * Message en parametre
     * @Retour boolean
     */
    
    @PUT
    @Path("postCroak")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces("text/plain")
    public String postCroak (JAXBElement<Croak> c);
    
    
    /**
     *Abonnement a un utilisateur (mettre à jour les abonnements etc.. + verifie l'existence)
     *Nom de l'utilisateur où s'abonner en param + nom de celui qui s'abonne
    */
    @PUT
    @Path("abonnement")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces("text/plain")
    public String abonnement(JAXBElement<MultiCroakos> listAbo);
    
    
    /**
     *Abonnement a un utilisateur (mettre à jour les abonnements etc.. + verifie l'existence)
     *Nom de l'utilisateur où s'abonner en param + nom de celui qui s'abonne
    */
    @PUT
    @Path("desabonnement")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces("text/plain")
    public String desabonnement(JAXBElement<MultiCroakos> listAbo);
    
    //Recuperation de la liste des tweets (les tweets de ses abonnement)
    //Nom d'utilisateur courrant en parametre
    @GET
    @Path("getCroaks/{name}")
    @Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    public CroakList getCroaks(@PathParam("name") String name);
}

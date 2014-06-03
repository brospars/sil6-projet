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
}

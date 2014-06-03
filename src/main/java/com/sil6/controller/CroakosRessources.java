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
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author ben
 */
public interface CroakosRessources extends Remote{
    // Retourne la liste des utilisateurs en json
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    List<Croakos> getCroakos();
}

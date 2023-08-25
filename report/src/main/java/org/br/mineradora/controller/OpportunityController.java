package org.br.mineradora.controller;

import java.util.Date;

import org.br.mineradora.service.OpportunityService;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.ServerErrorException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/apportunity")
public class OpportunityController {

    @Inject
    OpportunityService opportunityService;
    

    @GET
    @Path("/report")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response generateReport(){
        
        try {

            return Response.ok(opportunityService.generateCSVOportunityReport(),
                    MediaType.APPLICATION_OCTET_STREAM)
                .header("content-disposition","attachment; filename = "+ new Date() +"--oportunidades-venda.csv")
                .build();
            
        } catch (ServerErrorException e) {

            return Response.serverError().build();

        }
    }


}

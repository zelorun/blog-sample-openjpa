/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apogado.blogjpa.services;

import com.apogado.blogjpa.commons.Person;
import com.apogado.blogjpa.commons.PersonService;
import java.net.URI;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author GAbriel
 */
@Path("/person")
public class BlogServices {

    private static final Logger logger = Logger.getLogger(BlogServices.class.getName());

    @Path("/")
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
//@Consumes({"application/json","application/xml"})
    public Response list() {
        try {

            List<Person> p = (this.personService.getPersonRecords());
            GenericEntity<List<Person>> pp = new GenericEntity<List<Person>>(p) {
            };
            return Response.ok(pp).build();
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "list", ex);
            return Response.status(500).entity(ex.toString()).build();
        }
    }

    @Path("/{id}")
    @GET
    @Produces({"application/json", "application/xml"})
//@Consumes({"application/json","application/xml"})
    public Response get(@PathParam("id") String id) {
        Person p = this.personService.getPerson(Integer.parseInt(id));
        if (p == null) {
            return Response.status(404).entity("Not found").build();
        } else {
            return Response.ok(p).build();
        }
    }

    @Path("/")
    @POST
    @Produces({"application/json", "application/xml"})
    @Consumes({"application/json", "application/xml"})
    public Response create(Person p) {
        Person p2 = this.personService.createPerson(p);
        return Response.created(URI.create("/person/" + String.valueOf(p2.getId()))).entity(p2).build();
    }
    
    @Path("/{id}") @DELETE 
    public Response deletePerson(@PathParam("id") String id) {
        try {
            this.personService.deletePerson(Integer.parseInt(id));
            return Response.ok().build();
        }
        catch(Exception ex) {
            return Response.serverError().entity(ex).build();
        }
    }
    
    PersonService personService;

    public PersonService getPersonService() {
        return personService;
    }

    public void setPersonService(PersonService personService) {
        this.personService = personService;
    }
}

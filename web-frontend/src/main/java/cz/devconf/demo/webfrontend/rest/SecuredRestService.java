package cz.devconf.demo.webfrontend.rest;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

@Path("/securedRestService")
public class SecuredRestService {

    @GET
    @Produces("text/plain")
    @RolesAllowed({"Admin", "Remover"})
    public String getPrincipal(@Context SecurityContext sc) {
        return "Hello world!\n" +
               "Your name: " + sc.getUserPrincipal() + "\n" + // null when not logged
               "Is Admin: " + sc.isUserInRole("Admin") + "\n" +
               "Is Remover: " + sc.isUserInRole("Remover") + "\n";
    }

}

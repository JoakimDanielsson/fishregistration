package se.kits.fishregistration.boundary;

/**
 * Created by joakim on 2016-11-11.
 */
import se.kits.fishregistration.boundary.UserManager;
import se.kits.fishregistration.entity.User;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.net.URI;
import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Stateless
@Path("/users")
public class UserResource {

    @Inject
    private UserManager userManager;

    @GET
    @Produces(APPLICATION_JSON)
    @Path("/")
    public Response getUsers() {
        List<User> users = userManager.getUsers();
        return Response.ok(users).build();
    }

    @GET
    @Produces(APPLICATION_JSON)
    @Path("/{id}")
    public Response getUserById(@PathParam("id") Long id) {
        User user = userManager.getUserById(id);
        return Response.ok(user).build();
    }

    @POST
    @Path("/")
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public Response createUser(User user) {
        final User newUser = userManager.createUser(user);
        return Response.created(URI.create("/users/" + newUser.getId())).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteUser(@PathParam("id") Long id) {
        userManager.deleteUser(id);
        return Response.noContent().build();
    }
}

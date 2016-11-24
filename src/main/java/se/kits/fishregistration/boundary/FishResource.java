package se.kits.fishregistration.boundary;

/**
 * Created by joakim on 2016-10-24.
 */

import se.kits.fishregistration.entity.Fish;
import se.kits.fishregistration.entity.User;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;


@Stateless
@Path("/fish")
public class FishResource {

    @Inject
    private FishManager fishManager;

    @Inject
    private UserManager userManager;

    @GET
    @Produces(APPLICATION_JSON)
    @Path("/")
    public Response getAllFish() {
        List<Fish> fish = fishManager.getAllFish();
        return Response.ok(fish).build();
    }
    @POST
    @Path("/")
    public Response createFish(Fish fish) {
        final Fish newFish = fishManager.createFish(fish);
        return Response.created(URI.create("/fish/" + newFish.getId())).build();
    }
//    @POST
//    @Path("/{weight}/{length}/{longitude}/{latitude}/{species}/{userId}")
//    public Response createFish(
//            @PathParam("weight") double weight,
//            @PathParam("length") double length,
//            @PathParam("longitude") double longitude,
//            @PathParam("latitude") double latitude,
//            @PathParam("species") String species,
//            @PathParam("userId") Long userId) {
//        User user = userManager.getUserById(userId);
//        final Fish fish = fishManager.createFish(weight, length, longitude,
//                latitude, species, user);
//        return Response.created(URI.create("/fish/" + fish.getId())).build();
//    }

    @DELETE
    @Path("/{id}")
    public Response deleteFish(@PathParam("id") Long id) {
        fishManager.deleteFish(id);
        return Response.noContent().build();
    }
}

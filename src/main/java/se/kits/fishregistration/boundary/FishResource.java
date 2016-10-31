package se.kits.fishregistration.boundary;

/**
 * Created by joakim on 2016-10-24.
 */

import se.kits.fishregistration.entity.Fish;

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

    @GET
    @Produces(APPLICATION_JSON)
    @Path("/")
    public Response getAllFish() {
        List<Fish> fish = fishManager.getAllFish();
        return Response.ok(fish).build();
    }

//    @GET
//    @Produces(APPLICATION_JSON)
//    @Path("/{species}")
//    public Response getFishBySpecies(@PathParam("species") String species) {
//        List<Fish> fish = fishManager.getFishBySpecies(species);
//        return Response.ok(fish).build();
//    }


    @GET
    @Produces(APPLICATION_JSON)
    @Path("/{species}")
    public Response getFishBySpecies(@PathParam("species") String species) {
        int fishNr = fishManager.getNrFishBySpecies(species);
        return Response.ok(fishNr).build();
    }

    @POST
    @Path("/{weight}/{length}/{longitude}/{latitude}/{species}")
    public Response createFish(
            @PathParam("weight") double weight,
            @PathParam("length") double length,
            @PathParam("longitude") double longitude,
            @PathParam("latitude") double latitude,
            @PathParam("species") String species) {
        final Fish fish = fishManager.createFish(weight, length, longitude,
                                                 latitude, species);
        return Response.created(URI.create("/fish/" + fish.getId())).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteFish(@PathParam("id") Long id) {
        fishManager.deleteFish(id);
        return Response.noContent().build();
    }
}

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
import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

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

    @POST
    @Path("/")
    public Response createFish(Fish fish) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Gothenburg"));
        fish.setDate(new Date(calendar.getTime().getTime()));
        final Fish newFish = fishManager.createFish(fish);
        return Response.created(URI.create("/fish/" + newFish.getId())).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteFish(@PathParam("id") Long id) {
        fishManager.deleteFish(id);
        return Response.noContent().build();
    }
}

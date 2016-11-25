package se.kits.fishregistration.boundary;

import se.kits.fishregistration.entity.BlogPost;
import se.kits.fishregistration.entity.User;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import java.net.URI;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

/**
 * Created by joakim on 2016-11-16.
 */

@Stateless
@Path("/blog")
public class BlogPostResource {

    @Inject
    private BlogPostManager blogPostManager;

    @Inject
    private UserManager userManager;

    @GET
    @Produces(APPLICATION_JSON)
    @Path("/")
    public Response getAllBlogPosts() {
        List<BlogPost> blogPosts = blogPostManager.getAllBlogPosts();
        return Response.ok(blogPosts).build();
    }

    @POST
    @Path("/")
    public Response createBlogPost(BlogPost blogPost) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Gothenburg"));
        blogPost.setDate(new Date(calendar.getTime().getTime()));
        final BlogPost newBlogPost = blogPostManager.createBlogPost(blogPost);
        return Response.created(URI.create("/blog/" + newBlogPost.getId())).build();
    }

    @DELETE
    @Path("/{blogId}")
    public Response deleteBlogPost(@PathParam("blogId") Long id) {
        blogPostManager.deleteBlogPost(id);
        return Response.noContent().build();
    }
}

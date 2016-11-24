package se.kits.fishregistration.boundary;

import se.kits.fishregistration.entity.BlogPost;
import se.kits.fishregistration.entity.User;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import java.net.URI;
import java.util.List;

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
    @Path("/{userId}/{blogText}")
    public Response createBlogPost(
            @PathParam("userId") Long userId,
            @PathParam("blogText") String blogText) {
        User user = userManager.getUserById(userId);
        final BlogPost blogPost = blogPostManager.createBlogPost(user, blogText);
        return Response.created(URI.create("/blog/" + blogPost.getId())).build();
    }

//    @POST
//    @Consumes(APPLICATION_JSON)
//    @Path("/")
//    public Response createBlogPost(JsonObject obj) {
//        Long userId = Long.parseLong(obj.getString("userId"));
//        String blogText = obj.getString("blogText");
//        User user = userManager.getUserById(userId);
//        final BlogPost blogPost = blogPostManager.createBlogPost(user, blogText);
//        return Response.created(URI.create("/blog/" + blogPost.getId())).build();
//    }

    @DELETE
    @Path("/{blogId}")
    public Response deleteBlogPost(@PathParam("blogId") Long id) {
        blogPostManager.deleteBlogPost(id);
        return Response.noContent().build();
    }
}

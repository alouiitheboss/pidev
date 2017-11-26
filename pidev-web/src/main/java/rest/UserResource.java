package rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import persistence.User;
import service.UserManagment;

@Path("users")
public class UserResource {
	
	private final String BASE_URL = "http://localhost:18080/pidev-web/api/";
	Client client = ClientBuilder.newClient();
	
	@EJB
	private UserManagment userManagment;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllUsers(){
		
		List<User> users = userManagment.getAllUsers();
		return users == null ? Response.noContent().entity("no users found").build() :
							  Response.ok().entity(users).build();
		
	}
	
	@POST
	@Path("/add")
	@Consumes(MediaType.APPLICATION_JSON)
	public void addEstablishment(User user){
			userManagment.addUser(user);
    }
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("login")
	public Response testSecurityMethod(User user) throws URISyntaxException{
		
		WebTarget userEndpointTarget =  client.target(URI.create(BASE_URL)).path("authentication");
		Response response = userEndpointTarget.request(MediaType.APPLICATION_JSON)
				.post(Entity.entity(user, MediaType.APPLICATION_JSON));
		return Response.ok().entity(response.getHeaderString("Authorization")).build();
		
	}
	/*@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("check-token")
	public Response checkToken(TokenDto token){
		System.out.println(token);
		User user = null ;
		try{	
			String tokenForUser = token.getToken().substring("Bearer".length())
					.trim();
				
			user = tokenService.getUser(tokenForUser);
		}catch(Exception e){ 
			return Response.status(Status.UNAUTHORIZED).entity("Invalid verification code!")
			.build();
		}
		return Response.ok().entity(user.getId()).build();		
	}*/
	
}

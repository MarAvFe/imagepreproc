package main.java.com.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path("prifu")
public class ImageIO {
 
	@GET
	@Path("/testocv")
	public Response tryocv() {
		String[] args = {};
		FiltroGeneralizado.main(args);
		String output = "";
		return Response.status(200).entity(output).build();
	}
 
}
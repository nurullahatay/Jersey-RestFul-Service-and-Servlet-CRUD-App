package jersey.resources;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import dto.CustomerDTO;
import service.ServiceFacede;

@Path("/customers")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class Customers {

    @GET
    public List<CustomerDTO> getCustomers() throws Exception {
        return new ArrayList<CustomerDTO>(ServiceFacede.getInstance().getCustomers());
    }

    @GET
    @Path("/{customerId}")
    public CustomerDTO getCustomer(@PathParam("customerId") long customerId) throws Exception {
        return ServiceFacede.getInstance().getCustomer(customerId);
    }
    @POST
    public void  addCustomer(CustomerDTO customerDTO) throws Exception{
    	 ServiceFacede.getInstance().addCustomer(customerDTO);
    }

    @POST
    @Path("/{customerId}")
    public void updateCustomer(@PathParam("customerId") long customerId ,CustomerDTO customerDTO) throws Exception{
    	 ServiceFacede.getInstance().updateCustomer(customerDTO);
    }

    @DELETE
    @Path("/{customerId}")
    public void deleteCustomer(@PathParam("customerId") long customerId) throws Exception{
    	ServiceFacede.getInstance().removeCustomer(customerId);
    }

	
}

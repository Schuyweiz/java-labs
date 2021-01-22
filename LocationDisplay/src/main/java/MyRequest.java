import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;



public class MyRequest {

    public MyRequest(String url){
        if(url==null){
            System.out.println("URL cannot be an empty string.");
            System.exit(1);
        }
        this.url = url;
    }

    /**
     * Creates a client and sends a request on his behalf, then transforms the response into a String.
     * @return String value of a response to a client request.
     */
    public String getJson(){
        Client client = ClientBuilder.newClient();
        Response response = client.target(this.url).request().acceptLanguage("ru","en").get();

        return response.readEntity(String.class);
    }

    //URL of the request.
    private String url;

}

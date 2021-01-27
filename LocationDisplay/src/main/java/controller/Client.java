package controller;

import javax.ws.rs.core.Response;

public class Client {


    public Client() {
        this.webClient = javax.ws.rs.client.ClientBuilder.newClient();

    }

    /**
     * Sends a request to a given URL
     * and extracts Russian or English version of the page content.
     *
     * @return PAge content as a JSON string.
     */
    public String requestGet() {
        Response response = webClient.target(this.URL).request().acceptLanguage("ru", "en").get();
        return response.readEntity(String.class);
    }


    /**
     * Closes web client to avoid leaking resources.
     */
    public void closeWebClient() {
        this.webClient.close();
    }

    private javax.ws.rs.client.Client webClient;
    private static final String URL = "https://freegeoip.app/json/";
}

package phonebook.backend;

import org.apache.log4j.Logger;

public class Response {

    private final Logger LOGGER = Logger.getLogger(this.getClass());

    public Response(boolean accepted, String body){
        LOGGER.debug("Response object created.");
        this.accepted = accepted;
        this.body = body;
    }

    private boolean accepted;
    private String body;


    public boolean isAccepted() {
        return accepted;
    }

    public String getBody() {
        return body;
    }
}

package phonebook.backend;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.log4j.Logger;
import phonebook.entity.Contact;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

public class Database {

    private final Logger LOGGER = Logger.getLogger(this.getClass());
    /**
     * Executes a request handler based on the request type.
     * @param request a request to the datbase.
     * @return database response containing response status and json response.
     * @throws JsonProcessingException
     */
    public Response executeRequest(Request request) throws JsonProcessingException {
        LOGGER.debug("Executing one of the 3 request types.");

        if (request instanceof RequestGet){
            return executeRequest((RequestGet) request);
        }
        else if(request instanceof RequestPut){
            return executeRequest((RequestPut) request);
        }
        else if(request instanceof RequestDelete){
            return executeRequest((RequestDelete) request);
        }
        else{
            throw new IllegalArgumentException();
        }
    }

    public Database(){

        LOGGER.debug("Create database object.");
        this.data = new ArrayList<>();
        this.objectMapper = new ObjectMapper();
        loadData();
        Runtime.getRuntime().addShutdownHook(this.saveDataHook);
    }


    /**
     * Executes a get request by applying a corresponding filter to the data in the database.
     * @param rq request containing a filtering function and a filtering pattern.
     * @return a response containing request status and json body.
     * @throws JsonProcessingException
     */
    private Response executeRequest(RequestGet rq) throws JsonProcessingException {

        LOGGER.debug("Executing GET request");
        List<Contact> filteredData = rq.filterData(this.data);
        String body = objectMapper.writeValueAsString(filteredData);
        boolean isAccepted = !filteredData.isEmpty();

        LOGGER.debug("Returning a response to the GET request.");
        return new Response(isAccepted,body);
    }


    /**
     * Executes a PUT request by adding a corresponding object to the database.
     * @param rp request containing an object to add to the database.
     * @return a response containing response status and an empty json body.
     * @throws JsonProcessingException
     */
    private Response executeRequest(RequestPut rp) throws JsonProcessingException {

        LOGGER.debug("Executing a PUT request.");
        boolean isAccepted = rp.addContact(this.data);
        ObjectNode emptyNode = objectMapper.createObjectNode();
        String body  = objectMapper.writeValueAsString(emptyNode);

        this.saveData();
        LOGGER.debug("Returning a response to the PUT request.");
        return new Response(isAccepted, body);
    }


    /**
     * Executes a DELETE request by deleting a corresponding object from the database.
     * @param rd a request containing an object to delete.
     * @return a response containing response status and a json body.
     * @throws JsonProcessingException
     */
    private Response executeRequest(RequestDelete rd) throws JsonProcessingException {

        LOGGER.debug("Executing a DELETE request.");
        boolean isAccepted = rd.deleteContact(this.data);
        ObjectNode emptyNode = objectMapper.createObjectNode();
        String body = objectMapper.writeValueAsString(emptyNode);

        this.saveData();
        LOGGER.debug("Returning a request to the DELETE request.");
        return new Response(isAccepted, body);
    }

    //TODO:change file path
    private static final String SAVE_FILE_NAME = "target/contacts.json";
    /**
     * Loads contacts as a list of Contact type objects.
     */
    //TODO: change exception messages

    /**
     * Loads data to the database from a selected file.
     */
    private void loadData(){
        LOGGER.debug("Loading data.");
        try{
            data = objectMapper.readerForListOf(Contact.class).readValue(Paths.get(SAVE_FILE_NAME).toFile());
        } catch (IOException e) {
            System.out.println("Something went wrong with your contacts.\n" +
                    "Your contact book will now be erased and reinitialized empty.");
            LOGGER.error("An error occurred when loading data.\n"+e.getMessage());
            data = new ArrayList<>();
            saveData();
        }
    }

    /**
     * Saves list of contacts user is currently working with in a file.
     */
    private void saveData(){
        LOGGER.debug("Saving data.");
        File directory = new File("target");

        if(!directory.exists()){
            directory.mkdir();
        }
        try{
            objectMapper.writeValue(new File(SAVE_FILE_NAME),data);
        } catch (IOException e) {
            System.out.println("An error occurred when transforming data into a file.");
            LOGGER.error("An error occurred when saving data." + e.getMessage());
        }
    }

    private List<Contact> data;
    private final ObjectMapper objectMapper;
    private final Thread saveDataHook = new Thread(()->saveData());

}

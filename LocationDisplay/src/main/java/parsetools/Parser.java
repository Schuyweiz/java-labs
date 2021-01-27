package parsetools;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entity.Location;


public class Parser {

    public Parser() {
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Parses a json string into a usrdata.Location type object.
     *
     * @param json String containing data to be parsed into a location object.
     * @return usrdata.Location object with the data given in the json string.
     * @throws JsonProcessingException
     */
    public Location parseLocation(String json) throws JsonProcessingException {
        return objectMapper.readValue(json, Location.class);
    }

    private ObjectMapper objectMapper;


}

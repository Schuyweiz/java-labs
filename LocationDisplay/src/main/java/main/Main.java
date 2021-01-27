package main;

import com.fasterxml.jackson.core.JsonProcessingException;
import controller.Client;
import entity.Location;
import parsetools.Parser;

public class Main {


    public static void main(String[] args) {
        Client client = new Client();
        String json = client.requestGet();
        Parser parser = new Parser();

        try {
            Location location =
                    parser.parseLocation(json);
            System.out.println(location.toString());
        } catch (JsonProcessingException ex) {
            System.out.println("There was an error in processing JSON.\n" +
                    "Try with an URL containing correct data.");
        } finally {
            client.closeWebClient();
        }

    }
}

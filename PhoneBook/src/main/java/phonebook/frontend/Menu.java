package phonebook.frontend;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import phonebook.backend.Request;
import phonebook.backend.Response;

import java.util.Scanner;

public abstract class Menu {

    public abstract Request run() throws JsonProcessingException;
    public abstract void executeResponse(Response response) throws JsonProcessingException;
    protected Scanner in;
    protected ObjectMapper objectMapper;
}

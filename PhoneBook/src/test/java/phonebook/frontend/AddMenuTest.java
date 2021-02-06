package phonebook.frontend;

import mockit.Mocked;
import org.junit.jupiter.api.Test;
import phonebook.backend.Database;
import phonebook.backend.Response;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class AddMenuTest {

    private static final String RESPONSE_ACCEPTED = "Contact added successfully";

    private static final String RESPONSE_REJECTED = "Contact already exists in the database";
    private static final String SAVE_FILE_NAME = "target/contacts.json";
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();


    AddMenu menu = new AddMenu();
    @Test
    void executeResponse() throws IOException {
        File file = new File(SAVE_FILE_NAME);
        file.createNewFile();

        Database db = new Database();
        Response response = new Response(true,"");
        System.setOut(new PrintStream(outContent));
        menu.executeResponse(response);
        assertTrue(response.isAccepted());
        assertEquals(RESPONSE_ACCEPTED,
                outContent.toString()
                        .trim());
    }

    @Test
    void run() {
    }
}
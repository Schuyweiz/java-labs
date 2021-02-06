package phonebook.frontend;

import org.junit.jupiter.api.Test;
import phonebook.backend.Database;
import phonebook.backend.Response;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class DeleteMenuTest {
    private static final String SAVE_FILE_NAME = "target/contacts.json";
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private static final String SUCCESS_MESSAGE ="Deleted successfully.";
    private static final String FAILURE_MESSAGE = "Failed to delete a contact.";
    @Test
    void executeResponseOk() throws IOException {
        File file = new File(SAVE_FILE_NAME);
        file.createNewFile();
        System.setOut(new PrintStream(outContent));

        Response response = new Response(true,"");
        DeleteMenu menu = new DeleteMenu();
        menu.executeResponse(response);

        Database db = new Database();

        assertEquals(SUCCESS_MESSAGE,
                outContent.toString()
                        .trim());
    }

    @Test
    void executeResponseFail() throws IOException {
        File file = new File(SAVE_FILE_NAME);
        file.createNewFile();
        System.setOut(new PrintStream(outContent));

        Response response = new Response(false,"");
        DeleteMenu menu = new DeleteMenu();
        menu.executeResponse(response);

        Database db = new Database();

        assertEquals(FAILURE_MESSAGE,
                outContent.toString()
                        .trim());
    }
}
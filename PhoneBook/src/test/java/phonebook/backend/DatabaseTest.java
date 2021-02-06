package phonebook.backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import phonebook.entity.Contact;

import java.io.*;
import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseTest {
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final String SAVE_FILE_NAME = "target/contacts.json";
    private Random rng = new Random();
    public Contact createContact(){
        String name = RandomStringUtils.randomAlphabetic(10);
        String surname = RandomStringUtils.randomAlphabetic(10);
        String patrName = RandomStringUtils.randomAlphabetic(10);
        String email = RandomStringUtils.randomAlphabetic(15);
        String birthday = RandomStringUtils.randomAlphanumeric(8);
        String number = RandomStringUtils.randomNumeric(10);
        List<String> nums = new ArrayList<>();
        nums.add(number);
        int amt = new Random().nextInt(10);
        for(int i=0;i<amt;i++){
            nums.add(RandomStringUtils.randomNumeric(10));
        }
        return new Contact(name,surname,patrName,email,nums,birthday);

    }
    @Test
    void createDbDeletedFile() throws IOException {
        File file = new File(SAVE_FILE_NAME);
        file.delete();
        System.setOut(new PrintStream(outContent));
        Database db = new Database();
        assertEquals("Something went wrong with your contacts.\n" +
                "Your contact book will now be erased and reinitialized empty.",
                outContent.toString()
                .trim());
        assertTrue(file.exists());
    }

    @Test
    void createDbCorruptedFile() throws IOException{
        File file = new File(SAVE_FILE_NAME);
        file.createNewFile();
        FileWriter fw = new FileWriter(SAVE_FILE_NAME);
        fw.write("nice cock awesome balls");
        System.setOut(new PrintStream(outContent));
        Database db = new Database();
        assertEquals("Something went wrong with your contacts.\n" +
                        "Your contact book will now be erased and reinitialized empty.",
                outContent.toString()
                        .trim());
        assertTrue(file.exists());
    }

    @Test
    void createDb() throws IOException {
        File file = new File(SAVE_FILE_NAME);
        file.createNewFile();
        System.setOut(new PrintStream(outContent));
        Database db = new Database();
        assertEquals("",
                outContent.toString()
                        .trim());
        assertTrue(file.exists());
    }

    @Test
    void executeRequest() throws IOException {
        File file = new File(SAVE_FILE_NAME);
        file.delete();
        file.createNewFile();

        Database db = new Database();
        Contact contact1 = createContact();
        Contact contact2 = createContact();
        Contact contact3 = createContact();
        List<Contact> contacts = new ArrayList<>();
        contacts.add(contact1);
        contacts.add(contact2);
        contacts.add(contact3);

        Request requestPut = new RequestPut(contact1);
        Response responsePut = db.executeRequest(requestPut);
        assertTrue(responsePut.isAccepted());
        db.executeRequest(new RequestPut(contact2));
        db.executeRequest(new RequestPut(contact3));

        Request requestGetAll = new RequestGet(new BiFunction<List<Contact>, String, List<Contact>>() {
            @Override
            public List<Contact> apply(List<Contact> contacts, String s) {
                return FilterFunctions.searchBySurname(contacts,"");
            }
        });
        Response responseGetAll = db.executeRequest(requestGetAll);
        Collections.sort(contacts);
        String expectedJSON = new ObjectMapper().writeValueAsString(contacts);

        assertEquals(expectedJSON,responseGetAll.getBody());



    }

    @Test
    void executeRequestGet() throws IOException {
        File file = new File(SAVE_FILE_NAME);
        file.delete();
        file.createNewFile();

        Database db = new Database();
        List<Contact> contacts = new ArrayList<>();

        for(int i=0;i<20;i++){
            Contact contact = createContact();
            String pattern = RandomStringUtils.randomAlphabetic(1);
            Request req = new RequestGet(new BiFunction<List<Contact>, String, List<Contact>>() {
                @Override
                public List<Contact> apply(List<Contact> contacts, String s) {
                    return FilterFunctions.searchBySurname(contacts, s);
                }
            }, pattern);

            contacts.add(contact);
            db.executeRequest(new RequestPut(contact));

            Response response = db.executeRequest(req);
            List<Contact> temp = contacts.stream()
                    .filter(c->c.containsSurname(pattern))
                    .sorted()
                    .collect(Collectors.toList());
            assertEquals(response.getBody(),objectMapper.writeValueAsString(temp));

        }

        for(int i=0;i<20;i++){
            String pattern = RandomStringUtils.randomNumeric(2);
            Request req = new RequestGet(new BiFunction<List<Contact>, String, List<Contact>>() {
                @Override
                public List<Contact> apply(List<Contact> contacts, String s) {
                    return FilterFunctions.searchByBirth(contacts, s);
                }
            }, pattern);

            Response response = db.executeRequest(req);
            List<Contact> temp = contacts.stream()
                    .filter(c->c.containsBirth(pattern))
                    .sorted()
                    .collect(Collectors.toList());
            assertEquals(response.getBody(),objectMapper.writeValueAsString(temp));
        }

        for(int i=0;i<20;i++){
            String pattern = RandomStringUtils.randomNumeric(2);
            Request req = new RequestGet(new BiFunction<List<Contact>, String, List<Contact>>() {
                @Override
                public List<Contact> apply(List<Contact> contacts, String s) {
                    return FilterFunctions.searchByNumber(contacts, s);
                }
            }, pattern);

            Response response = db.executeRequest(req);
            List<Contact> temp = contacts.stream()
                    .filter(c->c.containsNumber(pattern))
                    .sorted()
                    .collect(Collectors.toList());
            assertEquals(response.getBody(),objectMapper.writeValueAsString(temp));
        }


    }

    @Test
    void executeRequestDelete() throws IOException {
        File file = new File(SAVE_FILE_NAME);
        file.delete();
        file.createNewFile();

        Database db = new Database();
        List<Contact> contacts = new ArrayList<>();

        for(int i=0;i<20;i++){
            Contact contact = createContact();
            String pattern = RandomStringUtils.randomAlphabetic(1);
            Request req = new RequestGet(new BiFunction<List<Contact>, String, List<Contact>>() {
                @Override
                public List<Contact> apply(List<Contact> contacts, String s) {
                    return FilterFunctions.searchBySurname(contacts, s);
                }
            }, pattern);

            contacts.add(contact);
            db.executeRequest(new RequestPut(contact));

            Response response = db.executeRequest(req);
            List<Contact> temp = contacts.stream()
                    .filter(c->c.containsSurname(pattern))
                    .sorted()
                    .collect(Collectors.toList());
            assertEquals(response.getBody(),objectMapper.writeValueAsString(temp));

        }
        for(int i=0;i<20;i++){
            Contact contactDelete = contacts.get(rng.nextInt(contacts.size()));
            Request req = new RequestDelete(contactDelete);

            Response response = db.executeRequest(req);
            if(contacts.size()>0){
                assertTrue(response.isAccepted());
            }
            contacts.remove(contactDelete);
            Request requestGetAll = new RequestGet(new BiFunction<List<Contact>, String, List<Contact>>() {
                @Override
                public List<Contact> apply(List<Contact> contacts, String s) {
                    return FilterFunctions.searchBySurname(contacts,"");
                }
            });
            Response checker = db.executeRequest(requestGetAll);
            Collections.sort(contacts);
            assertEquals(checker.getBody(),objectMapper.writeValueAsString(contacts));

        }
    }





}
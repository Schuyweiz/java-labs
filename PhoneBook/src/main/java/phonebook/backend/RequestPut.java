package phonebook.backend;

import org.apache.log4j.Logger;
import phonebook.entity.Contact;

import java.util.List;

public class RequestPut extends Request {
    private final Logger LOGGER = Logger.getLogger(this.getClass());

    private final Contact newContact;

    public RequestPut(Contact newContact){
        LOGGER.debug("RequestPut object created.");
        this.newContact = newContact;
    }

    public boolean addContact(List<Contact> contacts){
        LOGGER.debug("addContact method started.");
        if(contacts.contains(newContact)){
            return false;
        }
        return contacts.add(newContact);
    }

}

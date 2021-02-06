package phonebook.backend;

import org.apache.log4j.Logger;
import phonebook.entity.Contact;

import java.util.List;
import java.util.Optional;

public class RequestDelete extends Request {

    private final Logger LOGGER = Logger.getLogger(this.getClass());

    private Optional<Contact> deleteContact;

    public RequestDelete(Contact deleteContact){
        LOGGER.debug("RequestDelete object created.");
        this.deleteContact = Optional.of(deleteContact);
    }
    public RequestDelete(){
        LOGGER.debug("RequestDelete object created.");
        deleteContact = Optional.empty();
    }

    public boolean deleteContact(List<Contact> contacts){
        LOGGER.debug("deleteContact method started.");
        if(deleteContact.isEmpty()){
            return false;
        }
        Contact contact = deleteContact.get();
        System.out.println(contact.equals(contacts.get(0)));
        if(contacts.contains(contact)){
            contacts.remove(contact);
            return true;
        }
        else{
            return false;
        }
    }

    public String toString(){
        LOGGER.debug("toString method started.");
        String contact = deleteContact.isPresent()?
                deleteContact.get().toString() :
                "is null.";

        return String.format("Delete request. Contact to delete is %s",
                contact);
    }
}

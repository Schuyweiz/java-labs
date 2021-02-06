package phonebook.backend;

import phonebook.entity.Contact;

import java.util.List;

public interface Filtering {

    List<Contact> searchAction(List<Contact> contacts, String pattern);

}

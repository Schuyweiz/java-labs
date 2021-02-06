package phonebook.backend;

import phonebook.entity.Contact;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FilterFunctions {

    public FilterFunctions(){
    }


    public static List<Contact> searchBySurname(List<Contact> contacts, String pattern){
        Stream<Contact> contactStream = contacts.stream();
        return contactStream
        .filter(contact -> contact.containsSurname(pattern))
        .sorted()
        .collect(Collectors.toList());
    }

    public static List<Contact> searchByNumber(List<Contact> contacts, String pattern){
        Stream<Contact> contactStream = contacts.stream();
        return contactStream
                .filter(contact -> contact.containsNumber(pattern))
                .sorted()
                .collect(Collectors.toList());

    }

    public static List<Contact> searchByBirth(List<Contact> contacts,String pattern){
        Stream<Contact> contactStream = contacts.stream();
        return contactStream
                .filter(contact -> contact.containsBirth(pattern))
                .sorted()
                .collect(Collectors.toList());
    }





}

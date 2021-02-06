package phonebook.backend;

import phonebook.entity.Contact;

import java.util.List;
import java.util.function.BiFunction;

public abstract class Request {

     protected BiFunction<List<Contact>, String, List<Contact>> filter;
}

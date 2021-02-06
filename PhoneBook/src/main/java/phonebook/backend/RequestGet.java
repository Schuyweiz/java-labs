package phonebook.backend;

import org.apache.log4j.Logger;
import phonebook.entity.Contact;

import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public class RequestGet extends Request{

    private final Logger LOGGER = Logger.getLogger(this.getClass());

    public RequestGet(BiFunction<List<Contact>, String, List<Contact>> filterFunction, String pattern){
        LOGGER.debug("RequestGet object created.");
        this.filter = filterFunction;
        this.pattern = Optional.of(pattern);
    }

    public RequestGet(BiFunction<List<Contact>, String, List<Contact>> filterFunction){
        LOGGER.debug("RequestGet object created.");
        this.filter = filterFunction;
    }

    private Optional<String> pattern = Optional.empty();


    public List<Contact> filterData(List<Contact> contacts) {
        LOGGER.debug("filterData method started.");
        if(pattern.isEmpty()){
            return this.filter.apply(contacts,"");
        }
        List<Contact> filteredData = this.filter.apply(contacts,pattern.get());
        return filteredData;
    }

    public List<Contact> getAll(List<Contact> contacts){
        return contacts;
    }
}

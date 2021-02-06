package phonebook.frontend;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import phonebook.backend.*;
import phonebook.entity.Contact;
import phonebook.frontend.constants.Messages;

import java.util.List;
import java.util.Scanner;
import java.util.function.BiFunction;

public class SearchMenu extends Menu {

    private final Logger LOGGER = Logger.getLogger(this.getClass());

    @Override
    public Request run(){
        LOGGER.debug("Running user interaction.");
        initializeSearch();
        int choice = getMenuInput();
        String pattern = patterns[choice-1].getPattern();

        return new RequestGet(
                new BiFunction<List<Contact>, String, List<Contact>>() {
                    @Override
                    public List<Contact> apply(List<Contact> contacts, String s) {
                            return filters[choice-1].searchAction(contacts,s);
                    }
                },
                pattern
        );
    }

    @Override
    public void executeResponse(Response response) throws JsonProcessingException {
        LOGGER.debug("Executing response.");
        if(response.isAccepted()){
            String json = response.getBody();
            List<Contact> contacts = objectMapper.readValue(json, new TypeReference<List<Contact>>() {});
            for(int i=0;i<contacts.size();i++){
                System.out.printf("[%d] %s\n",i+1,contacts.get(i).toString());
            }
        }
        else{
            System.out.println("Not found.");
        }
    }


    //TODO: add to ctor
    private void initializePatternGetters(){
        LOGGER.debug("Pattern getters initialized.");
        this.patterns = new PatternInput[]{
                new PatternInput() {
                    @Override
                    public String getPattern() {
                        return getNameInput();
                    }
                },
                new PatternInput() {
                    @Override
                    public String getPattern() {
                        return getNumberInput();
                    }
                },
                new PatternInput() {
                    @Override
                    public String getPattern() {
                        return getBirthInput();
                    }
                }
        };
    }
    private void initializeQueries(){

        LOGGER.debug("Queries initialized.");
        filters = new Filtering[]{
                new Filtering() {
                    @Override
                    public List<Contact> searchAction(List<Contact> contacts, String pattern){
                        return FilterFunctions.searchBySurname(contacts, pattern);
                    }
                },
                new Filtering() {
                    @Override
                    public List<Contact> searchAction(List<Contact> contacts, String pattern){
                        return FilterFunctions.searchByNumber(contacts,pattern);
                    }
                },
                new Filtering() {
                    @Override
                    public List<Contact> searchAction(List<Contact> contacts, String pattern){
                        return FilterFunctions.searchByBirth(contacts, pattern);
                    }
                }

        };
    }

public SearchMenu(){
        LOGGER.debug("Search Menu object created.");
       this.in = new Scanner(System.in);
       this.objectMapper = new ObjectMapper();
        initializePatternGetters();
        initializeQueries();
}

    /**
     * Parses user input in the search menu section and retuns a chosen option
     * as an integer.
     * @return the number of a chosen option.
     */
    public int getMenuInput(){
        LOGGER.debug("getMenuInput method started input parsing.");
        String input = in.nextLine();
        while(!input.matches("[1-3]")){
            System.out.println(Messages.MENU_INPUT_WARNING);
            input = in.nextLine();
        }
        LOGGER.debug("getMenuInput method parsed the input.");
        return Integer.parseInt(input);
    }

    private void initializeSearch(){
        System.out.println(MENU);
    }


    /**
     * Parses user input until it corresponds to the search type provided
     * e.g. in this case its a letters only name.
     * @return pattern for the name.
     */
    private String getNameInput(){
        System.out.println("Enter the name patter to look for:");
        String input = in.nextLine();
        while(!input.matches("\\p{L}+")){
            System.out.println(Messages.PATTERN_INPUT_WARNING);
            input = in.nextLine();
        }
        return input;
    }

    /**
     * Parses user input until it corresponds to the search type provided
     * e.g. in this case its a digits only input.
     * @return pattern for the phone number.
     */
    private String getNumberInput(){
        System.out.println("Enter the number pattern to look for: ");
        String input = in.nextLine();
        while(!input.matches("\\d+")){
            System.out.println(Messages.PATTERN_INPUT_WARNING);
            input = in.nextLine();
        }
        return input;
    }

    /**
     * Parses user input until it corresponds to the search type provided
     * e.g. in this case its a digits only input.
     * @return pattern for the phone number.
     */
    private String getBirthInput(){
        String input = in.nextLine();
        while(!birthChecker(input)){
            System.out.println(Messages.PATTERN_INPUT_WARNING);
            input = in.nextLine();
        }
        return input;
    }

    private boolean birthChecker(String birth){
        return birth.matches("\\d{2}.\\d{2}.\\d{2}");
    }

    private PatternInput[] patterns;
    private Filtering[] filters;
    private static final String MENU = "Search by:\n" +
            "[1] Name.\n" +
            "[2] Phone number.\n" +
            "[3] Birth date.";

    interface PatternInput {
        String getPattern();
    }


}

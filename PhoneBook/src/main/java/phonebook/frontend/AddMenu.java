package phonebook.frontend;

import org.apache.log4j.Logger;
import phonebook.backend.Request;
import phonebook.backend.RequestPut;
import phonebook.backend.Response;
import phonebook.entity.Contact;
import phonebook.frontend.constants.Messages;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AddMenu extends Menu {
    private final Logger LOGGER = Logger.getLogger(this.getClass());


    @Override
    public void executeResponse(Response response) {
        LOGGER.debug("Executing response "+response.isAccepted());
        if(response.isAccepted()){
            System.out.println(RESPONSE_ACCEPTED);
        }
        else{
            System.out.println(RESPONSE_REJECTED);
        }
    }


    public AddMenu(){
        LOGGER.debug("Created add menu object.");
        in = new Scanner(System.in);
    }

    @Override
    public Request run() {

        LOGGER.debug("Running user interaction.");
        String[] nameSurPatr =getName();
        String number = getNumber();
        String birth = getBirth();
        String email = getEmail();
        List<String> numbers = getExtraNumbers();
        numbers.add(number);
        Contact contact = new Contact(
                nameSurPatr[0],
                nameSurPatr[1],
                nameSurPatr[2],
                email,
                numbers,
                birth);
        LOGGER.debug("Contact data gathered.");
        return new RequestPut(contact);
    }





    // region Messages
    private static final String RESPONSE_ACCEPTED = "Contact added successfully";

    private static final String RESPONSE_REJECTED = "Contact already exists in the database";

    private static final String GET_NAME_MESSAGE =
            "Enter the surname name and patronymic name of the contact, separated by whitespace: ";
    private static final String GET_BIRTH_MESSAGE =
            "Enter the birth date of a new contact in the format of dd.mm.yy: ";

    private static final String GET_EMAIL_MESSAGE =
            "Enter the email of a new contact: ";

    private static final String GET_NUMBER_MESSAGE =
            "Enter the number of a new contact: ";

    private static final String GET_EXTRA_NUMBERS_MESSAGE =
            "Enter another number of a new contact if any " +
                    "or enter \"no\" to finish creating a new contact";
    private static final String STOP_EXTRA_MESSAGE =
            "If you are done entering extra phone numbers, type in \"no\" and press enter.\n" +
                    "Type in a new number and press enter otherwise";
    // endregion

    // region Getters
    private String[] getName(){

        System.out.println(GET_NAME_MESSAGE);
        String name = in.nextLine();
        while (!nameChecker(name)){
            System.out.println(Messages.PATTERN_INPUT_WARNING);
            name = in.nextLine();
        }
        return name.split(" ");

    }
    private String getNumber(){
        System.out.println(GET_NUMBER_MESSAGE);
        String number = in.nextLine();
        while (!number.matches("\\d+")){
            System.out.println(Messages.PATTERN_INPUT_WARNING);
            number = in.nextLine();
        }
        return number;
    }
    private String getBirth(){
        System.out.println(GET_BIRTH_MESSAGE);
        String birth = in.nextLine();
        while (!birthChecker(birth)){
            System.out.println(Messages.PATTERN_INPUT_WARNING);
            birth = in.nextLine();
        }
        return birth;
    }
    private String getEmail(){
        System.out.println(GET_EMAIL_MESSAGE);
        String email = in.nextLine();
        while (!emailChecker(email)){
            System.out.println(Messages.PATTERN_INPUT_WARNING);
            email = in.nextLine();
        }
        return email;
    }
    private List<String> getExtraNumbers(){
        System.out.println(GET_EXTRA_NUMBERS_MESSAGE);
        List<String> numbers = new ArrayList<>();
        String number = in.nextLine();

        while (!number.equals("no")){

            if(extraNumsChecker(number)){
                numbers.add(number);
            }
            number = in.nextLine();
        }
        return numbers;
    }
    // endregion

    // region Checkers
    private boolean extraNumsChecker(String number){
        boolean matches =number.matches("\\d+");
        if(!matches){
            System.out.println(Messages.PATTERN_INPUT_WARNING);
        }
        return matches;
    }
    private boolean emailChecker(String email){
        return email.matches("\\p{L}+@\\p{L}+.\\p{L}+");
    }
    private boolean birthChecker(String birth){
        return birth.matches("\\d{2}.\\d{2}.\\d{2}");
    }
    private boolean nameChecker(String name){
        return name.matches("\\p{L}+\\s\\p{L}+\\s\\p{L}+");
    }
    //endregion

}

package phonebook.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.stream.Collectors;


public class Contact implements Comparable<Contact>{


    public boolean containsSurname(String pattern){
        return this.surname.toLowerCase().contains(pattern.toLowerCase());
    }

    public boolean containsNumber(String pattern){
        for(String number:this.numbers){
            if(number.contains(pattern)){
                return true;
            }
        }
        return false;
    }

    public boolean containsBirth(String pattern){
        return this.birth.contains(pattern);
    }

    public Contact(){}
    public Contact(String name,
                   String surname,
                   String patronymicName,
                   String email,
                   List<String> numbers,
                   String birth){

        this.name = name;
        this.surname = surname;
        this.patronymicName = patronymicName;
        this.email = email;
        this.numbers = numbers;
        this.birth = birth;
    }

    @JsonProperty("name")
    private String name;

    @JsonProperty("surname")
    private String surname;

    @JsonProperty("patronymicName")
    private String patronymicName;

    @JsonProperty("email")
    private String email;

    @JsonProperty("numbers")
    private List<String> numbers;

    @JsonProperty("birth")
    private String birth;



    public String toString(){
        String bio =  String.format("Surname: %s |" +
                " Name: %s |" +
                " Patronymic name: %s" +
                " \n\tEmail: %s" +
                " \n\tBirth date: %s" +
                " \n\tPhone numbers: ",
                surname, name, patronymicName, email, birth
        );
        String numbersString = numbers
                .stream()
                .collect(Collectors.joining(" | "));
        return bio + numbersString;

    }

    @Override
    public int compareTo(Contact o) {
        return this.surname.compareTo(o.surname);
    }

    @Override
    public boolean equals(Object contact){
        if(contact instanceof Contact){
            return compareField((Contact) contact);
        }
        return false;
    }

    private boolean compareField(Contact contact){
        return compareNumber(contact)
                && this.surname.equals(contact.surname)
                && this.name.equals(contact.name)
                && this.patronymicName.equals((contact.patronymicName))
                && this.birth.equals(contact.birth)
                && this.email.equals(contact.email);
    }
    private boolean compareNumber(Contact contact){
        if(this.numbers.size()!=contact.numbers.size()){
            return false;
        }
        for(int i=0;i<this.numbers.size();i++){
            if(!this.numbers.get(i).equals(contact.numbers.get(i))){
                return false;
            }
        }
        return true;
    }
}

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import parsetools.Parser;
import entity.Location;

import java.util.Locale;
import java.util.Random;


class ParserTest {

    @Test
    void testParserCorrectInput(){
        Random rng = new Random();
        Parser parser = new Parser();
        Location location;
        boolean assertion = true;
        String country, region, city;
        Double lat,lon;
        String json, answer;
        for(int i =0; i<100;i++){
            country = RandomStringUtils.randomAlphabetic(rng.nextInt(50));
            region = RandomStringUtils.randomAlphabetic(rng.nextInt(50));
            city = RandomStringUtils.randomAlphabetic(rng.nextInt(50));
            lat = rng.nextDouble() + rng.nextInt(89);
            lon = rng.nextDouble() + rng.nextInt(89);

            json = String.format(Locale.US, "{\"country_name\":\"%s\"," +
                    "\"region_name\":\"%s\"," +
                    "\"city\":\"%s\"," +
                    "\"latitude\":%f," +
                    "\"longitude\":%f}",
                    country,region,city,lat,lon);

            answer = String.format("Страна: %s\n" +
                            "Область: %s\n" +
                            "Город: %s\n" +
                            "Широта: %.4f\n" +
                            "Долгота: %.4f",
                    country,region,city,lat,lon);

            try {
                location = parser.parseLocation(json);
                assertion = answer.equals(location.toString());
            } catch (JsonProcessingException e) {
                assertion = false;
                System.out.println(i);
            }

            Assertions.assertTrue(assertion);

        }

    }

    @Test
    void testParserRandomInput() throws JsonProcessingException {
        String s = "wefretrhyjukythgrefwrgethryjtuyhrefwgrethrytu";
        Parser parser = new Parser();
        boolean assertion = true;
        try {
            Location location = parser.parseLocation(s);
        }
        catch (JsonParseException ex){
            assertion = false;
        }

        Assertions.assertFalse(assertion);
    }

    static class ParserWrongDataType{
        @Test
        void testWrongCountryDataType() throws JsonProcessingException {
            String s = "{\"country_name\":\"123\"," +
                    "\"region_name\":\"dfghj\"," +
                    "\"city\":\"Канны\"," +
                    "\"latitude\":12," +
                    "\"longitude\":13.34}";
            Parser parser = new Parser();
            boolean assertion;
            try{
                Location location = parser.parseLocation(s);
                assertion = true;
            }
            catch (MismatchedInputException ex){
                assertion = false;
            }

            Assertions.assertFalse(assertion);

        }
        @Test
        void WrongRegionNameDataType() throws JsonProcessingException {
            String s = "{\"country_name\":\"вапро\"," +
                    "\"region_name\":\"123\"," +
                    "\"city\":\"Канны\"," +
                    "\"latitude\":12," +
                    "\"longitude\":13.34}";
            Parser parser = new Parser();
            boolean assertion;
            try{
                Location location = parser.parseLocation(s);
                assertion = true;
            }
            catch (MismatchedInputException ex){
                assertion = false;
            }

            Assertions.assertFalse(assertion);
        }

        @Test
        void WrongCityDataType() throws JsonProcessingException {
            String s = "{\"country_name\":\"вапро\"," +
                    "\"region_name\":\"dfghj\"," +
                    "\"city\":\"123\"," +
                    "\"latitude\":12," +
                    "\"longitude\":13.34}";
            Parser parser = new Parser();
            boolean assertion;
            try{
                Location location = parser.parseLocation(s);
                assertion = true;
            }
            catch (MismatchedInputException ex){
                assertion = false;
            }

            Assertions.assertFalse(assertion);
        }

        @Test
        void WrongLongitudeDataType() throws JsonProcessingException {
            String s = "{\"country_name\":\"вапро\"," +
                    "\"region_name\":\"dfghj\"," +
                    "\"city\":\"Канны\"," +
                    "\"latitude\":12," +
                    "\"longitude\":dfgh}";
            Parser parser = new Parser();
            boolean assertion;
            try{
                Location location = parser.parseLocation(s);
                assertion = true;
            }
            catch (JsonParseException ex){
                assertion = false;
            }

            Assertions.assertFalse(assertion);
        }@Test
        void WrongLatitudeDataType() throws JsonProcessingException {
            String s = "{\"country_name\":\"вапро\"," +
                    "\"region_name\":\"dfghj\"," +
                    "\"city\":\"Канны\"," +
                    "\"latitude\":sdfghj," +
                    "\"longitude\":13.34}";
            Parser parser = new Parser();
            boolean assertion;
            try{
                Location location = parser.parseLocation(s);
                assertion = true;
            }
            catch (JsonParseException ex){
                assertion = false;
            }

            Assertions.assertFalse(assertion);
        }
    }


}
import org.junit.Assert;
import org.junit.Test;

public class ParserTest {

        @Test
        public void ParserCorrectInput() {

            String json = String.format("{\"ip\":\"2a01:cb1d:371:4500:3d4a:8587:92d:3f89\"," +
                    "\"country_code\":\"FR\"," +
                    "\"country_name\":\"Страна\"," +
                    "\"region_code\":\"PAC\"," +
                    "\"region_name\":\"Регион\"," +
                    "\"city\":\"Город\"," +
                    "\"zip_code\":\"06400\"," +
                    "\"time_zone\":\"Europe/Paris\"," +
                    "\"latitude\":123.123," +
                    "\"longitude\":123.321," +
                    "\"metro_code\":0}");

            Parser parser = new Parser(json);
            Assert.assertEquals(parser.extractCountry(), "Страна");
            Assert.assertEquals(parser.extractCity(),"Город");
            Assert.assertEquals(parser.extractRegion(), "Регион");
            Double latitude = 123.123;
            Assert.assertEquals(parser.extractLatitude(), latitude);
            Double longitude = 123.321;
            Assert.assertEquals(parser.extractLongitude(),longitude);
        }
        @Test(expected=IllegalArgumentException.class)
        public void parserIncorrectInput(){
            String json = String.format("{\"ip\":\"2a01:cb1d:371:4500:3d4a:8587:92d:3f89\"," +
                    "\"country_code\":\"FR\"," +
                    //Error
                    "\"country_\":\"Страна\"," +
                    "\"region_code\":\"PAC\"," +
                    "\"region_name\":\"Регион\"," +
                    "\"city\":\"Город\"," +
                    "\"zip_code\":\"06400\","+
                    "\"time_zone\":\"Europe/Paris\"," +
                    "\"latitude\":123.123," +
                    "\"longitude\":123.321," +
                    "\"metro_code\":0}");
            Parser parser = new Parser(json);
            String s = parser.extractCountry();

        }


}
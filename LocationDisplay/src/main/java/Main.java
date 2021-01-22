import java.io.IOException;

public class Main {

    private static final String url = "https://freegeoip.app/json/";

    public static void main(String[] args) throws IOException {
        MyRequest request = new MyRequest(url);
        String json = request.getJson();

        try {
            Parser parser = new Parser(json);
            Location location = new Location(
                    parser.extractCountry(),
                    parser.extractRegion(),
                    parser.extractCity(),
                    parser.extractLongitude(),
                    parser.extractLatitude()
            );
            System.out.println(location.toString());
        }
        catch (IllegalArgumentException ex){
            System.out.println(ex.getMessage());
        }
    }
}

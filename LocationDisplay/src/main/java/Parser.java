import java.util.IllegalFormatCodePointException;

public class Parser {

    public Parser(String parserInput){
        this.data = parserInput.split(",");
        if(this.data.length<5){
            throw new IllegalArgumentException();
        }
    }


    /**
     * Finds and extracts data about current country.
     * @return Country name.
     * @throws IllegalArgumentException in case there is no country field.
     */
    public String extractCountry() throws IllegalArgumentException{
        for(String id: data){
            if(id.contains("country_name")){
                return id.split(":")[1].replace("\"","");
            }
        }
        throw new IllegalArgumentException();
    }

    /**
     * Finds and extracts data about current region.
     * @return Region name.
     * @throws IllegalArgumentException in case there is no region field.
     */
    public String extractRegion() throws IllegalArgumentException {
        for(String id: data){
            if(id.contains("region_name")){
                return id.split(":")[1].replace("\"","");
            }
        }
        throw new IllegalArgumentException();
    }

    /**
     * Finds and extracts data about current city.
     * @return City name.
     * @throws IllegalArgumentException in case there is no city field.
     */
    public String extractCity() throws IllegalArgumentException{
        for(String id: data){
            if(id.contains("city")){
                return id.split(":")[1].replace("\"","");
            }
        }
        throw new IllegalArgumentException();
    }

    /**
     * Finds and extracts data about current longitude.
     * @return Longitude value.
     * @throws IllegalArgumentException in case there is no longitude field or
     * wrong data type is presented
     */
    public Double extractLongitude() throws IllegalArgumentException{
        for(String id: data){
            if(id.contains("longitude")){
                return Double.parseDouble(id.split(":")[1]);
            }
        }
        throw new IllegalArgumentException();
    }

    /**
     * Finds and extracts data about current latitude.
     * @return Longitude value.
     * @throws IllegalArgumentException in case there is no latitude field or
     * wrong data type is presented
     */
    public Double extractLatitude() throws IllegalArgumentException{
        for(String id: data){
            if(id.contains("latitude")){
                return Double.parseDouble(id.split(":")[1]);
            }
        }
        throw new IllegalArgumentException();
    }


    private String[] data;

}

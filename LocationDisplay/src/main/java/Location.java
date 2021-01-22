public class Location {


    public Location(String country, String region, String city, double longitude, double latitude){
        this.country = country;
        this.region = region;
        this.city = city;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String toString(){
        return String.format("Страна: %s\n" +
                        "Область: %s\n" +
                        "Город: %s\n" +
                        "Широта: %.4f\n" +
                        "Долгота: %.4f",
                        country,region,city,latitude,longitude);
    }

    private String country;
    private String region;
    private String city;
    private double longitude;
    private double latitude;


}

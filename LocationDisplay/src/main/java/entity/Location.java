package entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import parsetools.StringOnlyDeserializer;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Location {

    //need this ctor for testing
    public Location(String country, String region, String city, double longitude, double latitude) {
        this.country = country;
        this.region = region;
        this.city = city;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    //need this ctor to parse json into usrdata.Location.
    public Location() {
    }

    public String toString() {
        return String.format("Страна: %s\n" +
                        "Область: %s\n" +
                        "Город: %s\n" +
                        "Широта: %.4f\n" +
                        "Долгота: %.4f",
                country, region, city, latitude, longitude);
    }

    @JsonDeserialize(using = StringOnlyDeserializer.class)
    @JsonProperty("country_name")
    private String country;

    @JsonDeserialize(using = StringOnlyDeserializer.class)
    @JsonProperty("region_name")
    private String region;

    @JsonDeserialize(using = StringOnlyDeserializer.class)
    @JsonProperty("city")
    private String city;

    @JsonProperty("longitude")
    private double longitude;

    @JsonProperty("latitude")
    private double latitude;


}

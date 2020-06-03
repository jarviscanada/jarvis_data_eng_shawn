package ca.jrvs.apps.twitter.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "longLat",
    "type"
})

public class Coordinates {

  @JsonProperty("coordinates")
  private double[] longLat;
  @JsonProperty("type")
  private String type;

  public Coordinates() {
  }

  public Coordinates(double[] longLat, String type) {
    setLongLat(longLat);
    setType(type);
  }

  @JsonProperty("coordinates")
  public double[] getLongLat() {
    return longLat;
  }

  @JsonProperty("coordinates")
  public void setLongLat(double[] coordinates) {
    this.longLat = coordinates;
  }

  @JsonProperty("type")
  public String getType() {
    return type;
  }

  @JsonProperty("type")
  public void setType(String type) {
    this.type = type;
  }
}

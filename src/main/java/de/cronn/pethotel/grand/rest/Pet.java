package de.cronn.pethotel.grand.rest;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
  @JsonSubTypes.Type(value = Dog.class),
  @JsonSubTypes.Type(value = Fish.class),
})
public interface Pet {
  long id();

  String name();
}

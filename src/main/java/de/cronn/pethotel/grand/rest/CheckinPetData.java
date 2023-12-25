package de.cronn.pethotel.grand.rest;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
  @JsonSubTypes.Type(value = CheckinDogData.class),
  @JsonSubTypes.Type(value = CheckinFishData.class),
})
public sealed interface CheckinPetData permits CheckinDogData, CheckinFishData {
  String name();
}

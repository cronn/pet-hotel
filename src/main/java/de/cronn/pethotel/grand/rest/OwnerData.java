package de.cronn.pethotel.grand.rest;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
@JsonSubTypes({
  @JsonSubTypes.Type(value = OwnerReference.class),
  @JsonSubTypes.Type(value = CheckinOwnerData.class),
})
public sealed interface OwnerData permits OwnerReference, CheckinOwnerData {}

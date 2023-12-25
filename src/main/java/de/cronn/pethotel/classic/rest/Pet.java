package de.cronn.pethotel.classic.rest;

import jakarta.validation.constraints.NotNull;

public record Pet(long id, @NotNull String name, Long chipNumber, @NotNull PetType type) {

  public static Pet newDog(long id, String name, long chipNumber) {
    return new Pet(id, name, chipNumber, PetType.DOG);
  }

  public static Pet newFish(long id, String name) {
    return new Pet(id, name, null, PetType.FISH);
  }
}

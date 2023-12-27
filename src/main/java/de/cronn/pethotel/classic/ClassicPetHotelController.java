package de.cronn.pethotel.classic;

import de.cronn.pethotel.classic.rest.*;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class ClassicPetHotelController implements ClassicPetHotel {
  private static final AtomicLong petId = new AtomicLong();
  private static final Map<Long, Pet> petHotel = new ConcurrentHashMap<>();

  private static long getNextPetId() {
    return petId.addAndGet(1);
  }

  static void reset() {
    petId.set(0);
  }

  @Override
  @PostMapping("classic/checkin")
  public GetPetsResponse checkin(@RequestBody @Valid CheckinRequest request) {
    List<CheckinPetData> pets = request.pets();
    return new GetPetsResponse(checkinPets(pets));
  }

  @Override
  @GetMapping("classic/pets")
  public GetPetsResponse getPets() {
    return new GetPetsResponse(new ArrayList<>(petHotel.values()));
  }

  public List<Pet> checkinPets(List<CheckinPetData> pets) {
    return pets.stream().map(this::checkinPet).toList();
  }

  private Pet checkinPet(CheckinPetData checkinPetData) {
    validate(checkinPetData);
    Pet pet = createPet(checkinPetData);
    petHotel.put(pet.id(), pet);
    return pet;
  }

  private void validate(CheckinPetData checkinPetData) {
    switch (checkinPetData.type()) {
      case PetType.DOG -> validateDogData(checkinPetData);
      case PetType.FISH -> validateFishData(checkinPetData);
    }
  }

  private void validateFishData(CheckinPetData checkinPetData) {
    if (checkinPetData.chipNumber() != null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }
  }

  private void validateDogData(CheckinPetData checkinPetData) {
    if (checkinPetData.chipNumber() == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }
  }

  private static Pet createPet(CheckinPetData checkinPetData) {
    return switch (checkinPetData.type()) {
      case PetType.DOG -> Pet.newDog(
          getNextPetId(), checkinPetData.name(), checkinPetData.chipNumber());
      case PetType.FISH -> Pet.newFish(getNextPetId(), checkinPetData.name());
    };
  }
}

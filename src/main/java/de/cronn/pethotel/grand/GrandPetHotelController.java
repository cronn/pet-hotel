package de.cronn.pethotel.grand;

import de.cronn.pethotel.grand.rest.*;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GrandPetHotelController implements GrandPetHotel {
  private static final AtomicLong petId = new AtomicLong();
  private static final Map<Long, Pet> petHotel = new ConcurrentHashMap<>();

  private static long getNextPetId() {
    return petId.addAndGet(1);
  }

  @Override
  @PostMapping("/checkin")
  public GetPetsResponse checkin(@RequestBody @Valid CheckinRequest request) {
    return new GetPetsResponse(checkinPets(request.pets()));
  }

  @Override
  @GetMapping("/pets")
  public GetPetsResponse getPets() {
    return new GetPetsResponse(new ArrayList<>(petHotel.values()));
  }

  private List<Pet> checkinPets(List<CheckinPetData> pets) {
    return pets.stream().map(this::checkinPet).toList();
  }

  private Pet checkinPet(CheckinPetData petData) {
    Pet pet = createPet(petData);
    petHotel.put(pet.id(), pet);
    return pet;
  }

  private static Pet createPet(CheckinPetData petData) {
    return switch (petData) {
      case CheckinDogData dog -> new Dog(getNextPetId(), dog.name(), dog.chipNumber());
      case CheckinFishData fish -> new Fish(getNextPetId(), fish.name());
    };
  }
}

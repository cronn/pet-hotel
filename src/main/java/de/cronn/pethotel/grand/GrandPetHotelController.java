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
  private static final AtomicLong currentId = new AtomicLong();
  private static final Map<Long, Pet> petHotel = new ConcurrentHashMap<>();
  private static final Map<Long, Owner> owners = new ConcurrentHashMap<>();

  private static long getNextId() {
    return currentId.addAndGet(1);
  }

  static void reset() {
    currentId.set(0);
  }

  @Override
  @PostMapping("/checkin")
  public GetPetsResponse checkin(@RequestBody @Valid CheckinRequest request) {
    long ownerId = createOrGetOwner(request.person());
    return new GetPetsResponse(checkinPets(ownerId, request.pets()));
  }

  private long createOrGetOwner(OwnerData person) {
    return switch (person) {
      case OwnerReference ownerRef -> getOwner(ownerRef).id();
      case CheckinOwnerData ownerData -> createAndSaveOwner(ownerData);
    };
  }

  private static Owner getOwner(OwnerReference ownerRef) {
    return owners.get(ownerRef.id());
  }

  private long createAndSaveOwner(CheckinOwnerData ownerData) {
    Owner owner = new Owner(getNextId(), ownerData.firstName(), ownerData.lastName());
    owners.put(owner.id(), owner);
    return owner.id();
  }

  @Override
  @GetMapping("/pets")
  public GetPetsResponse getPets() {
    return new GetPetsResponse(new ArrayList<>(petHotel.values()));
  }

  private List<Pet> checkinPets(long ownerId, List<CheckinPetData> pets) {
    return pets.stream().map(p -> checkinPet(ownerId, p)).toList();
  }

  private Pet checkinPet(long ownerId, CheckinPetData petData) {
    Pet pet = createPet(ownerId, petData);
    petHotel.put(pet.id(), pet);
    return pet;
  }

  private static Pet createPet(long ownerId, CheckinPetData petData) {
    return switch (petData) {
      case CheckinDogData dog -> new Dog(getNextId(), dog.name(), dog.chipNumber(), ownerId);
      case CheckinFishData fish -> new Fish(getNextId(), fish.name(), ownerId);
    };
  }
}

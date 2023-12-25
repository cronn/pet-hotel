package de.cronn.pethotel.grand;

import de.cronn.pethotel.grand.rest.CheckinRequest;
import de.cronn.pethotel.grand.rest.GetPetsResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface GrandPetHotel {
  @PostMapping("/checkin")
  GetPetsResponse checkin(@RequestBody @Valid CheckinRequest request);

  @GetMapping("/pets")
  GetPetsResponse getPets();
}

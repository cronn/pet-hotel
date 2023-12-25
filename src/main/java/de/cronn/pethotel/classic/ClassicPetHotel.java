package de.cronn.pethotel.classic;

import de.cronn.pethotel.classic.rest.CheckinRequest;
import de.cronn.pethotel.classic.rest.GetPetsResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface ClassicPetHotel {
  @PostMapping("classic/checkin")
  GetPetsResponse checkin(@RequestBody @Valid CheckinRequest request);

  @GetMapping("classic/pets")
  GetPetsResponse getPets();
}

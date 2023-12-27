package de.cronn.pethotel.grand;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.cronn.pethotel.IntegrationTestBase;
import de.cronn.pethotel.grand.rest.*;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GrandPetHotelControllerTest extends IntegrationTestBase {

  @BeforeEach
  void resetControllerState() {
    GrandPetHotelController.reset();
  }

  @Test
  void shouldCheckinPets() throws JsonProcessingException {
    CheckinRequest request =
        new CheckinRequest(
            List.of(checkinDogIo(), checkinFishBlub()), new CheckinOwnerData("Kylo", "Ren"));

    GetPetsResponse checkinResponse =
        restTemplate.postForObject("/checkin", request, GetPetsResponse.class);

    assertWithJsonFileWithSuffix(asJson(checkinResponse), "checkin_response");
    GetPetsResponse getResponse = restTemplate.getForObject("/pets", GetPetsResponse.class);
    assertWithJsonFileWithSuffix(asJson(getResponse), "getPets_response");
  }

  @Test
  void shouldCheckinPetsWithRecurringOwner() throws JsonProcessingException {
    CheckinRequest request =
        new CheckinRequest(List.of(checkinDogIo()), new CheckinOwnerData("Kylo", "Ren"));
    GetPetsResponse checkinResponse =
        restTemplate.postForObject("/checkin", request, GetPetsResponse.class);
    long exitingOwnerId = checkinResponse.pets().getFirst().ownerId();

    CheckinRequest request2 =
        new CheckinRequest(List.of(checkinFishBlub()), new OwnerReference(exitingOwnerId));
    restTemplate.postForObject("/checkin", request2, GetPetsResponse.class);

    GetPetsResponse getResponse = restTemplate.getForObject("/pets", GetPetsResponse.class);
    assertWithJsonFileWithSuffix(asJson(getResponse), "getPets_response");
  }

  private static CheckinFishData checkinFishBlub() {
    return new CheckinFishData("Blub");
  }

  private static CheckinDogData checkinDogIo() {
    return new CheckinDogData("Io", 12345L);
  }
}

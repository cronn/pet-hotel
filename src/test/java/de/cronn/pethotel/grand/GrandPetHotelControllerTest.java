package de.cronn.pethotel.grand;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.cronn.pethotel.IntegrationTestBase;
import de.cronn.pethotel.grand.rest.CheckinDogData;
import de.cronn.pethotel.grand.rest.CheckinFishData;
import de.cronn.pethotel.grand.rest.CheckinRequest;
import de.cronn.pethotel.grand.rest.GetPetsResponse;
import java.util.List;
import org.junit.jupiter.api.Test;

public class GrandPetHotelControllerTest extends IntegrationTestBase {

  @Test
  void shouldCheckinPets() throws JsonProcessingException {
    CheckinRequest request = new CheckinRequest(List.of(checkinDogIo(), checkinFishBlub()));

    GetPetsResponse checkinResponse =
        restTemplate.postForObject("/checkin", request, GetPetsResponse.class);

    assertWithJsonFileWithSuffix(asJson(checkinResponse), "checkin_response");
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

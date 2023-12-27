package de.cronn.pethotel.classic;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.cronn.pethotel.IntegrationTestBase;
import de.cronn.pethotel.classic.rest.CheckinPetData;
import de.cronn.pethotel.classic.rest.CheckinRequest;
import de.cronn.pethotel.classic.rest.GetPetsResponse;
import de.cronn.pethotel.classic.rest.PetType;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ClassicPetHotelControllerTest extends IntegrationTestBase {

  @BeforeEach
  void resetControllerState() {
    ClassicPetHotelController.reset();
  }

  @Test
  void shouldCheckinPets() throws JsonProcessingException {
    CheckinRequest request = new CheckinRequest(List.of(checkinDogIo(), checkinFishBlub()));

    GetPetsResponse checkinResponse =
        restTemplate.postForObject("/classic/checkin", request, GetPetsResponse.class);

    assertWithJsonFileWithSuffix(asJson(checkinResponse), "checkin_response");
    GetPetsResponse getResponse = restTemplate.getForObject("/classic/pets", GetPetsResponse.class);
    assertWithJsonFileWithSuffix(asJson(getResponse), "getPets_response");
  }

  @Test
  void shouldReturnBadRequestOnDogWithoutChipNumber() throws Exception {
    CheckinRequest request = new CheckinRequest(List.of(getDogWithNoChipNumber()));

    ResponseEntity<?> checkinResponse =
        restTemplate.postForEntity("/classic/checkin", request, null);

    Assertions.assertThat(checkinResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
  }

  @Test
  void shouldReturnBadRequestOnFishWithChipNumber() throws Exception {
    CheckinRequest request = new CheckinRequest(List.of(getFishWithChipNumber()));

    ResponseEntity<?> checkinResponse =
        restTemplate.postForEntity("/classic/checkin", request, null);

    Assertions.assertThat(checkinResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
  }

  private static CheckinPetData getDogWithNoChipNumber() {
    return new CheckinPetData("NoChipNumberDog", null, PetType.DOG);
  }

  private static CheckinPetData getFishWithChipNumber() {
    return new CheckinPetData("ChipNumberFish", 666L, PetType.FISH);
  }

  private static CheckinPetData checkinFishBlub() {
    return new CheckinPetData("Blub", null, PetType.FISH);
  }

  private static CheckinPetData checkinDogIo() {
    return new CheckinPetData("Io", 12345L, PetType.DOG);
  }
}

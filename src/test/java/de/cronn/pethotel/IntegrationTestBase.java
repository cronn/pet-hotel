package de.cronn.pethotel;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.cronn.assertions.validationfile.junit5.JUnit5ValidationFileAssertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegrationTestBase implements JUnit5ValidationFileAssertions {
  @Autowired protected TestRestTemplate restTemplate;
  @Autowired protected ObjectMapper objectMapper;

  protected String asJson(Object checkinResponse) throws JsonProcessingException {
    return objectMapper.writeValueAsString(checkinResponse);
  }
}

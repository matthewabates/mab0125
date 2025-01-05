package com.example.tool_rental;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ToolRentalApplicationTests {

	@Autowired
    MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	@Test
	void testCheckout() throws Exception {
		JsonNode testCases = objectMapper.readTree(new File("src/test/resources/checkout-tests.json"));

		for (JsonNode testCase : testCases) {
			String description = testCase.get("description").asText();
			System.out.println("Running test case: " + description);

			JsonNode request = testCase.get("request");
			String requestBody = objectMapper.writeValueAsString(request.get("body"));

			JsonNode response = testCase.get("response");
			int expectedStatus = response.get("status").asInt();
			String expectedResponseBody = objectMapper.writeValueAsString(response.get("body"));

			mockMvc.perform(post("/checkout")
						.content(requestBody)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is(expectedStatus))
				.andExpect(content().json(expectedResponseBody));
		}
	}

}

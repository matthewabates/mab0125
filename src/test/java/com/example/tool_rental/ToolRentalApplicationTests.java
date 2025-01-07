package com.example.tool_rental;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.DynamicTest.dynamicTest;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext
class ToolRentalApplicationTests {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	@TestFactory
	Stream<DynamicTest> testCheckout() throws Exception {
		List<Map<String, Object>> testCases = objectMapper.readValue(
				new File("src/test/resources/checkout-tests.json"),
				new TypeReference<>() {}
		);

		return testCases.stream().map(testCase -> dynamicTest((String) testCase.get("description"), () -> {
			JsonNode request = objectMapper.convertValue(testCase.get("request"), JsonNode.class);
			String requestBody = objectMapper.writeValueAsString(request.get("body"));

			JsonNode response = objectMapper.convertValue(testCase.get("response"), JsonNode.class);
			int expectedStatus = response.get("status").asInt();
			String expectedResponseBody = objectMapper.writeValueAsString(response.get("body"));

			mockMvc.perform(post("/checkout")
							.content(requestBody)
							.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().is(expectedStatus))
					.andExpect(content().json(expectedResponseBody));
		}));
	}

}

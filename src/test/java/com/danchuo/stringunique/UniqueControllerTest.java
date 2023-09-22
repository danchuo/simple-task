package com.danchuo.stringunique;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureMockMvc
public class UniqueControllerTest {


    @Autowired
    private MockMvc mockMvc;

    private static Map<Character, Integer> parseResponse(String response) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(response, new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new RuntimeException("Ошибка разбора ответа сервера", e);
        }
    }

    private static void assertDictionaryEquals(Map<Character, Integer> expected, Map<Character, Integer> actual) {
        assertEquals(expected.size(), actual.size());
        for (Map.Entry<Character, Integer> entry : expected.entrySet()) {
            Character key = entry.getKey();
            assertTrue(actual.containsKey(key));
            assertEquals(entry.getValue(), actual.get(key));
        }
    }

    @Test
    public void testValidInput() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/calculate-unique")
                        .param("input", "abc123"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testEmptyInput() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/calculate-unique")
                        .param("input", ""))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void testTooLongInput() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/calculate-unique")
                        .param("input", "a".repeat(101)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void testInvalidCharactersInput() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/calculate-unique")
                        .param("input", "abcпомогите123"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void testResponseMatchesExpectedDictionary() throws Exception {
        Map<Character, Integer> expectedDictionary = new HashMap<>();
        expectedDictionary.put('a', 2);
        expectedDictionary.put('b', 1);
        expectedDictionary.put('c', 1);

        mockMvc.perform(MockMvcRequestBuilders.get("/calculate-unique")
                        .param("input", "aabc"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(result -> {
                    String response = result.getResponse().getContentAsString();
                    Map<Character, Integer> responseDictionary = parseResponse(response);
                    assertDictionaryEquals(expectedDictionary, responseDictionary);
                });
    }

}

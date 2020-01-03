package com.practice.micro.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ExceptionHandlerController.class)
public class ExceptionHandlerControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private Gson gson;
	
	@Test
	public void handleMethodArgumentNotValidTest() throws Exception {
		String json = "{ \"id\": \"A\", \"name\": \"B\",\"additonalDetails\": \"B\" }";
		JsonObject convertedObject = gson.fromJson(json, JsonObject.class);
		mockMvc.perform(post("/post").accept(MediaType.APPLICATION_JSON).content(convertedObject.toString()).
				contentType(MediaType.APPLICATION_JSON)).
				andExpect(status().isBadRequest());
	}

}

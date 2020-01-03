package com.practice.micro.controller;

import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.practice.micro.model.SampleModel;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ExceptionHandlerController.class)
public class ExceptionHandlerControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private Gson gson;
	
	@Mock
	private SampleController sampleController;
	
	private SampleModel sample;
	
	
	 @BeforeEach
	  public void setUp() {
		    sample = new SampleModel();	
	        MockitoAnnotations.initMocks(this);
	        mockMvc = MockMvcBuilders.standaloneSetup(sampleController).setControllerAdvice(new ExceptionHandlerController())
	            .build();
	    }
	
	
	@SuppressWarnings("deprecation")
	@Test
	public void handleHttpMessageNotReadableTest() throws Exception {
		
		 Mockito.when(sampleController.postMeth(sample)).thenThrow(new HttpMessageNotReadableException("Request not valid"));
		mockMvc.perform(post("/post").accept(MediaType.APPLICATION_JSON).content(gson.toJson(sample)).
				contentType(MediaType.APPLICATION_JSON)).
				andExpect(status().isBadRequest());
	}
	
	@Test
	public void  handleMethodArgumentNotValidTest() throws Exception {
		MethodArgumentNotValidException methodArgumentNotValidException = mock(MethodArgumentNotValidException.class);
		Mockito.when(sampleController.postMeth(sample)).thenThrow(methodArgumentNotValidException);
		mockMvc.perform(post("/post").accept(MediaType.APPLICATION_JSON).content(gson.toJson(sample)).
				contentType(MediaType.APPLICATION_JSON)).
				andExpect(status().isBadRequest());
	}
	
	@Test
	@Disabled("Not implemented yet")
	public void handleNoHandlerTest() throws Exception {
		String json = "{ \"id\": 1, \"name\": \"B\",\"additonalDetails\": \"B\" }";
		JsonObject convertedObject = gson.fromJson(json, JsonObject.class);
		mockMvc.perform(put("/post").accept(MediaType.APPLICATION_JSON).content(convertedObject.toString()).
				contentType(MediaType.APPLICATION_JSON)).
				andExpect(status().isNotFound());
	}
	
	
	@Test
	@Disabled("Not implemented yet")
	public void handleNoHandlerTest1() throws Exception {
		mockMvc.perform(get("/get/1").
				contentType(MediaType.APPLICATION_JSON)).
				andExpect(status().isOk());
	}

}

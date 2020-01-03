package com.practice.micro.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class SampleModel {
	
	@NotNull(message = "id cannot be null")
	private Integer id;
	
	@NotBlank(message = "Name cannot be blank")
	private String name;
	
	@NotBlank(message = "additonalDetails cannot be blank")
	private String additonalDetails;

}

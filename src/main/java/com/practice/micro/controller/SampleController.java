package com.practice.micro.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.practice.micro.model.SampleModel;
import com.practice.micro.service.SampleService;

@RestController
public class SampleController {
	
	@Autowired
	private SampleService service;
	
	@PostMapping(value="/post")
	@ResponseBody
	public ResponseEntity<String> postMeth(@Valid @RequestBody SampleModel sample){
		
		return new ResponseEntity<>(service.serviceMeth(sample.toString()),HttpStatus.CREATED);
	}
	
	@GetMapping(value="/get/{id}")
	@ResponseBody
	public ResponseEntity<String> getMeth(@PathVariable(name="id",required=true) int id){
		return new ResponseEntity<>("Done",HttpStatus.OK);
	}

}

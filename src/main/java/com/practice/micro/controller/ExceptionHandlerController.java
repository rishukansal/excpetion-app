package com.practice.micro.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.practice.micro.model.ErrorDetails;

@ControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {
	
	@Override
	  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
	      HttpHeaders headers, HttpStatus status, WebRequest request) {
		 List<String> errors = new ArrayList<>();
		    ex.getBindingResult().getAllErrors().forEach(error -> errors.add(error.getDefaultMessage()));
		 ErrorDetails exceptionResponse = new ErrorDetails(errors, new Date(),HttpStatus.BAD_REQUEST.toString());
	    return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
	  }
	 
	 
	 @ExceptionHandler(MethodArgumentTypeMismatchException.class )
	 public ResponseEntity<Object> handleMethodArgumentTypeMismatch(
	   final MethodArgumentTypeMismatchException ex, final WebRequest request) {
	     String error = 
	       ex.getName() + " should be of type " + ex.getRequiredType().getName();
	     List<String> errors = new ArrayList<>();
	     errors.add(error);
	     ErrorDetails exceptionResponse = new ErrorDetails(errors, new Date(),HttpStatus.BAD_REQUEST.toString());
	     return new ResponseEntity<>(
	    		 exceptionResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	 }
	 
	 
	 @Override
	    protected ResponseEntity<Object> handleTypeMismatch(final TypeMismatchException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
	        //
	        final String error = ex.getValue() + " value for " + ex.getPropertyName() + " should be of type " + ex.getRequiredType();

	        List<String> errors = new ArrayList<>();
		     errors.add(error);
		     ErrorDetails exceptionResponse = new ErrorDetails(errors, new Date(),HttpStatus.BAD_REQUEST.toString());
		     return new ResponseEntity<>(
		    		 exceptionResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	    }
	 
	 
	 @Override
	    protected ResponseEntity<Object> handleConversionNotSupported(final ConversionNotSupportedException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
	        //
	        final String error = ex.getValue() + " value for " + ex.getPropertyName() + " should be of type " + ex.getRequiredType();

	        List<String> errors = new ArrayList<>();
		     errors.add(error);
		     ErrorDetails exceptionResponse = new ErrorDetails(errors, new Date(),HttpStatus.BAD_REQUEST.toString());
		     return new ResponseEntity<>(
		    		 exceptionResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	    }
	 
	 
	 @Override
	    protected ResponseEntity<Object> handleBindException(final BindException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
	        //
	        final List<String> errors = new ArrayList<>();
	        for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
	            errors.add(error.getField() + ": " + error.getDefaultMessage());
	        }
	        for (final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
	            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
	        }
	        ErrorDetails exceptionResponse = new ErrorDetails(errors, new Date(),HttpStatus.BAD_REQUEST.toString());
		     return new ResponseEntity<>(
		    		 exceptionResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	    }
	 
	 
	 @Override
	    protected ResponseEntity<Object> handleMissingServletRequestPart(final MissingServletRequestPartException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		 	final List<String> errors = new ArrayList<>();
	        final String error = ex.getRequestPartName() + " part is missing";
	        errors.add(error);
	        ErrorDetails exceptionResponse = new ErrorDetails(errors, new Date(),HttpStatus.BAD_REQUEST.toString());
		     return new ResponseEntity<>(
		    		 exceptionResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	 }
	 
	 @Override
	    protected ResponseEntity<Object> handleNoHandlerFoundException(final NoHandlerFoundException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		 final List<String> errors = new ArrayList<>();
	        final String error = "No handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL();

	        errors.add(error);
	        ErrorDetails exceptionResponse = new ErrorDetails(errors, new Date(),HttpStatus.BAD_REQUEST.toString());
		     return new ResponseEntity<>(
		    		 exceptionResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	    }
	 
	 
	 @Override
	    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(final HttpRequestMethodNotSupportedException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
	        //
		 final List<String> errors = new ArrayList<>();
	        final StringBuilder builder = new StringBuilder();
	        builder.append(ex.getMethod());
	        builder.append(" method is not supported for this request. Supported methods are ");
	        ex.getSupportedHttpMethods().forEach(t -> builder.append(t + " "));

	        errors.add(builder.toString());
	        ErrorDetails exceptionResponse = new ErrorDetails(errors, new Date(),"Method not allowed");
		     return new ResponseEntity<>(
		    		 exceptionResponse, new HttpHeaders(), HttpStatus.METHOD_NOT_ALLOWED);
	    }
	 
	 	@Override
	    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
	 		 final String error = ex.getMostSpecificCause().toString();
	 		 	StringBuilder stringBulder = new StringBuilder();
	 		 	stringBulder.append(ex.getLocalizedMessage()).append(error+ex.getMessage());
		        List<String> errors = new ArrayList<>();
			     errors.add(stringBulder.toString());
			     ErrorDetails exceptionResponse = new ErrorDetails(errors, new Date(),HttpStatus.BAD_REQUEST.toString());
			     return new ResponseEntity<>(
			    		 exceptionResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	    }
	 	
	 
	

}

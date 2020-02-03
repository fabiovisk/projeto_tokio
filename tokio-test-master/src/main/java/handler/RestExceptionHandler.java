package handler;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import exception.CustomerNotFoundDetails;
import exception.CustomerResourceException;
import exception.ValidationExceptionDetails;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(CustomerResourceException.class)
	public ResponseEntity<?> handleCustomerResourceException(CustomerResourceException crException){
		CustomerNotFoundDetails cnfDetails = CustomerNotFoundDetails.Builder
				.newBuilder()
				.timestamp(new Date().getTime())
				.status(HttpStatus.NOT_FOUND.value())
				.title("Customer NOT FOUND")
				.detail(crException.getMessage())
				.developerMessage(crException.getClass().getName())
				.build();
		return new ResponseEntity<>(cnfDetails, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException manvException){
		
		List<FieldError> fieldErrors = manvException.getBindingResult().getFieldErrors();
		String fields = fieldErrors.stream().map(FieldError::getField).collect(Collectors.joining(","));
		String fieldMessages = fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(","));
		ValidationExceptionDetails ceDetails = ValidationExceptionDetails.Builder
				.newBuilder()
				.timestamp(new Date().getTime())
				.status(HttpStatus.BAD_REQUEST.value())
				.title("Field Validation Error")
				.detail("Field Validation Error")
				.developerMessage(manvException.getClass().getName())
				.fieldMessage(fieldMessages)
				.field(fields)
				.build();
		return new ResponseEntity<>(ceDetails, HttpStatus.BAD_REQUEST);
	}
	
}

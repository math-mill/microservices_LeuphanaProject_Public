package de.leuphana.order_service.component.behaviour.exceptionhandling;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ControllerAdvice
public class OrderItemNotFoundAdvice {

	@ResponseBody
	@ExceptionHandler(OrderItemNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	String orderItemNotFoundHandler(OrderItemNotFoundException e) {
		return e.getMessage();
	}
}
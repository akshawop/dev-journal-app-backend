package me.akshawop.devjournalapp.modules.journalentries.exception.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;
import me.akshawop.devjournalapp.modules.journalentries.controller.JournalEntryController;
import me.akshawop.devjournalapp.shared.exception.AccessDeniedException;
import me.akshawop.devjournalapp.shared.exception.exceptionHandler.ErrorResponse;
import me.akshawop.devjournalapp.shared.exception.exceptionHandler.ResponseBuilder;

@Order(1)
@RestControllerAdvice(assignableTypes = JournalEntryController.class)
public class JournalEntryControllerExceptionHandler {

    @Autowired
    private ResponseBuilder builder;

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDenied(Exception ex, HttpServletRequest req) {

        return builder.buildResponse(
                HttpStatus.FORBIDDEN,
                "Forbidden",
                ex.getMessage(),
                req,
                null);
    }
}

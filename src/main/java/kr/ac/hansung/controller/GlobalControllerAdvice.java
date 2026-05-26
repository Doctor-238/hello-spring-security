package kr.ac.hansung.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ModelAttribute("now")
    public LocalDateTime now() {
        return LocalDateTime.now();
    }
}

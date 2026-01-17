package com.example.FileUpload;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/file")
public class controller {
    @GetMapping
    public String sayHello(){
        return "Hello";
    }
}

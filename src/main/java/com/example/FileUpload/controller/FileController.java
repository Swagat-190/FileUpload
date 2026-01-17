package com.example.FileUpload.controller;

import com.example.FileUpload.service.FileService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/files")
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file) {

        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("File is empty");
        }


        return ResponseEntity.ok(fileService.upload(file));
    }
    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestParam String publicId) {

        System.out.println("PUBLIC ID RECEIVED: " + publicId);

        fileService.delete(publicId);
        return ResponseEntity.ok("File deleted successfully");
    }

    @DeleteMapping("/delete/test")
    public String test() {
        System.out.println("DELETE TEST HIT");
        return "OK";
    }


}

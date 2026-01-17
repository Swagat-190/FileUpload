package com.example.FileUpload.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.web.multipart.MultipartFile;
@Service
public class FileService {
    private final Cloudinary cloudinary;

    public FileService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public Map<String, String> upload(MultipartFile file) {

        try {
            // Upload to Cloudinary
            Map<?, ?> uploadResult = cloudinary.uploader().upload(
                    file.getBytes(),
                    ObjectUtils.asMap(
                            "folder", "file-upload-service",   // optional folder
                            "resource_type", "auto"            // supports image/pdf/etc
                    )
            );

            // Prepare response
            Map<String, String> response = new HashMap<>();
            response.put("url", uploadResult.get("secure_url").toString());
            response.put("publicId", uploadResult.get("public_id").toString());

            return response;

        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file to Cloudinary", e);
        }
    }
    public String delete(String publicId) {
        try {
            Map<?, ?> result = cloudinary.uploader().destroy(
                    publicId,
                    ObjectUtils.asMap("resource_type", "image")
            );

            String status = result.get("result").toString();

            if ("ok".equals(status)) {
                return "File deleted successfully";
            }

            if ("not found".equals(status)) {
                throw new RuntimeException("File not found");
            }

            throw new RuntimeException("Unexpected Cloudinary response: " + status);

        } catch (Exception e) {
            throw new RuntimeException("Failed to delete file", e);
        }
    }

}


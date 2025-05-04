package com.recipedb.api.service;

import com.recipedb.api.dao.ImageDao;
import com.recipedb.api.model.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class ImageService {

    @Autowired
    private ImageDao imageDao;

    public Image saveImage(MultipartFile image) {
        Image img = null;
        try {
            img = Image.builder()
                    .id(UUID.randomUUID().toString())
                    .uploadDate(new Date()).data(image.getBytes())
                    .fileName(image.getName()).build();
        } catch (IOException e) {
            throw new RuntimeException("Error occurred while processing image");
        }
        return imageDao.save(img);
    }

    public String getById(String id) {
        return Optional.ofNullable(imageDao.getById(id))
                .map(Image::getData)
                .map(bytes -> Base64.getEncoder().encodeToString(bytes))
                .orElse(null);
    }
}

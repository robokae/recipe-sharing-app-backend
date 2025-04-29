package com.recipedb.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Image {

    @Id
    private int id;
    private Date uploadDate;
    private byte[] data;
    private String fileName;
}

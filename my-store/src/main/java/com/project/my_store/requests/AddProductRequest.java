package com.project.my_store.requests;

import com.project.my_store.model.Category;
import com.project.my_store.model.Image;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data       //for getters and setters, in entities this is not recommended instead we use @Getter and @Setter
public class AddProductRequest {
    //basically a copy of Product, because its better to work somewhere else and not
    //exactly in one of our main entity, just to be safe

    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;
    private Category category;
    private List<Image> images;
}

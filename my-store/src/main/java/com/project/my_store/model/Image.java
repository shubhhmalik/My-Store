package com.project.my_store.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Primary;

import java.sql.Blob;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)   //The DB automatically generates a unique value when a new row is added
    private Long id;
    private String fileName;
    private String fileType;

    @Lob
    private Blob image;
    private String downloadUrl;


    @ManyToOne                              //as one product has many images
    @JoinColumn(name = "product_id")
    private Product product;
}

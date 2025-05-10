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
    @GeneratedValue(strategy = GenerationType.IDENTITY)   
    private Long id;
    private String fileName;
    private String fileType;

    @Lob
    private Blob image;
    private String downloadUrl;


    @ManyToOne                              
    @JoinColumn(name = "product_id")
    private Product product;
}

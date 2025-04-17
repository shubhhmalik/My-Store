package com.project.my_store.service.image;

import com.project.my_store.dto.ImageDto;
import com.project.my_store.exceptions.ProductNotFoundException;
import com.project.my_store.exceptions.ResourceNotFoundException;
import com.project.my_store.model.Image;
import com.project.my_store.model.Product;
import com.project.my_store.repository.ImageRepository;
import com.project.my_store.service.product.ProductService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ImageService implements IImageService {

    private final ImageRepository imageRepository;
    private final ProductService productService;


    @Override
    public Image getImageById(Long id) {
        return imageRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Image Not Found!"));
    }

    @Override
    public void deleteImageById(Long id) {
        imageRepository.findById(id)
                .ifPresentOrElse(imageRepository :: delete, ()->{
                    throw new ResourceNotFoundException("Image Not Found!");
                });
        //the above simply means
        // Image image = imageRepository.findById(id)
        //         .orElseThrow(()-> new ResourceNotFoundException("Image Not Found!"));
        //imageRepository.delete(image);
    }



    @Override
    public List<ImageDto> saveImage(List<MultipartFile> files, Long productId) {
        Product product = productService.getProductById(productId);
        if (files == null || files.isEmpty()) {
            throw new ResourceNotFoundException("File Not Found!");
        }

        List<ImageDto> savedImageDtos = new ArrayList<>();
        String buildDownloadUrl = "/api/v1/images/image/download/";

        for (MultipartFile file : files) {
            try {
                // Create and save image
                Image image = new Image();
                image.setFileName(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                image.setImage(new SerialBlob(file.getBytes()));
                image.setProduct(product);

                Image savedImage = imageRepository.save(image);

                // Set download URL after we have the ID
                savedImage.setDownloadUrl(buildDownloadUrl + savedImage.getId());
                imageRepository.save(savedImage);

                // Create and add DTO using proper field names
                savedImageDtos.add(new ImageDto(
                        savedImage.getId(),       // maps to imageId
                        savedImage.getFileName(),  // maps to imageName
                        savedImage.getDownloadUrl()
                ));
            } catch (IOException | SQLException e) {
                throw new RuntimeException("Failed To Save Image: " + file.getOriginalFilename(), e);
            }
        }

        return savedImageDtos;
    }



    @Override
    public Image updateImage(MultipartFile file, Long imageId) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File Cannot Be Null Or Empty.");
        }
        Image image = getImageById(imageId);

        if (image == null) {
            throw new ResourceNotFoundException("Image Not Found!");
        }

        try {
            image.setFileName(file.getOriginalFilename());
            image.setFileType(file.getContentType());
            image.setImage(new SerialBlob(file.getBytes()));      //basically to convert binary data to SQL Blob object
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
        return imageRepository.save(image);    //just in case
    }
}

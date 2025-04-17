package com.project.my_store.service.image;

import com.project.my_store.dto.ImageDto;
import com.project.my_store.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageService {
    Image getImageById(Long id);
    void deleteImageById(Long id);
    List<ImageDto> saveImage(List<MultipartFile> files, Long productId);
    Image updateImage(MultipartFile file, Long imageId);

}

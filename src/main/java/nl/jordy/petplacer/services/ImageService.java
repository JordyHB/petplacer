package nl.jordy.petplacer.services;

import nl.jordy.petplacer.exceptions.BadRequestException;
import nl.jordy.petplacer.exceptions.RecordNotFoundException;
import nl.jordy.petplacer.models.Image;
import nl.jordy.petplacer.repositories.ImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ImageService {

    private final ImageRepository imageRepository;

    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public String uploadImage(MultipartFile imageFile) throws BadRequestException {

        Image image = new Image();

        try {
            if (imageFile.isEmpty()) {
                return "Please select a file to upload";
            }
            image.setName(imageFile.getOriginalFilename());
            image.setType(imageFile.getContentType());
            image.setImageData(imageFile.getBytes());
        } catch (IOException e) {
            throw new BadRequestException("Failed to upload image");
        }

        imageRepository.save(image);

        return "Image uploaded successfully: " + imageFile.getOriginalFilename();
    }

    public byte[] downloadImage(Long id) {

        Image image = imageRepository.findById(id).orElseThrow(
                () -> new RecordNotFoundException("No image found with id: " + id)
        );

        try {
            return image.getImageData();
        } catch (Exception e) {
            throw new RecordNotFoundException("Failed to download image");
        }
    }

}

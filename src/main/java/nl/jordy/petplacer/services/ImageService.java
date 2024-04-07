package nl.jordy.petplacer.services;

import nl.jordy.petplacer.exceptions.BadRequestException;
import nl.jordy.petplacer.exceptions.RecordNotFoundException;
import nl.jordy.petplacer.models.Image;
import nl.jordy.petplacer.models.ShelterPet;
import nl.jordy.petplacer.repositories.ImageRepository;
import nl.jordy.petplacer.util.AccessValidator;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ImageService {

    private final ImageRepository imageRepository;
    private final ShelterPetService shelterPetService;

    public ImageService(ImageRepository imageRepository, ShelterPetService shelterPetService) {
        this.imageRepository = imageRepository;
        this.shelterPetService = shelterPetService;
    }

    private Image handleImageFile(MultipartFile imageFile) throws BadRequestException {
        Image image = new Image();

        try {
            if (imageFile.isEmpty()) {
                throw new BadRequestException("Image file is empty");
            }
            image.setName(imageFile.getOriginalFilename());
            image.setType(imageFile.getContentType());
            image.setImageData(imageFile.getBytes());
        } catch (IOException e) {
            throw new BadRequestException("Failed to upload image");
        }

        return image;
    }

    public String uploadImage(MultipartFile imageFile) {

        Image image = handleImageFile(imageFile);

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

    public String uploadImageToShelterPet(Long shelterPetID,MultipartFile imageFile) {

        ShelterPet shelterPet = shelterPetService.fetchShelterPetByID(shelterPetID);
        AccessValidator.isSheltersManagerOrAdmin(AccessValidator.getAuth(), shelterPet.getShelter());

        if (shelterPet.getImage() != null) {
            throw new BadRequestException("ShelterPet already has an image");
        }

        Image image = handleImageFile(imageFile);
        image.setShelterPet(shelterPet);
        imageRepository.save(image);

        return "Image Successfully added to ShelterPet: " + shelterPet.getName();
    }
}

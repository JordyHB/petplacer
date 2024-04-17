package nl.jordy.petplacer.services;

import nl.jordy.petplacer.exceptions.BadRequestException;
import nl.jordy.petplacer.exceptions.RecordNotFoundException;
import nl.jordy.petplacer.helpers.modalmapper.ModelMapperHelper;
import nl.jordy.petplacer.models.Image;
import nl.jordy.petplacer.models.ShelterPet;
import nl.jordy.petplacer.models.UserOwnedPet;
import nl.jordy.petplacer.repositories.ImageRepository;
import nl.jordy.petplacer.util.AccessValidator;
import nl.jordy.petplacer.util.ImageUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ImageService {

    private final ImageRepository imageRepository;
    private final ShelterPetService shelterPetService;
    private final UserOwnedPetService userOwnedPetService;
    private final AccessValidator accessValidator;

    public ImageService(
            ImageRepository imageRepository,
            ShelterPetService shelterPetService,
            UserOwnedPetService userOwnedPetService,
            AccessValidator accessValidator
    ) {
        this.imageRepository = imageRepository;
        this.shelterPetService = shelterPetService;
        this.userOwnedPetService = userOwnedPetService;
        this.accessValidator = accessValidator;
    }

    private Image handleImageFile(MultipartFile imageFile) throws BadRequestException {
        Image image = new Image();

        try {
            if (imageFile.isEmpty()) {
                throw new BadRequestException("Image file is empty");
            }

            image.setName(imageFile.getOriginalFilename());
            image.setType(imageFile.getContentType());
            image.setImageData(ImageUtils.compressImage(imageFile.getBytes()));
        } catch (IOException e) {
            throw new BadRequestException("Failed to upload image");
        }

        return image;
    }

    private void validateUpdatePermission(Image image) {

        if (image == null) {
            throw new RecordNotFoundException("No image found to update");
        }

        if (image.getShelterPet() != null) {
            accessValidator.isSheltersManagerOrAdmin(accessValidator.getAuth(), image.getShelterPet().getShelter());
        } else if (image.getUserOwnedPet() != null) {
            accessValidator.isUserOrAdmin(accessValidator.getAuth(), image.getUserOwnedPet().getCurrentOwner().getUsername());
        }
    }

    public byte[] downloadImage(Long id) {

        Image image = imageRepository.findById(id).orElseThrow(
                () -> new RecordNotFoundException("No image found with id: " + id)
        );

        try {
            return ImageUtils.decompressImage(image.getImageData());
        } catch (Exception e) {
            throw new RecordNotFoundException("Failed to download image");
        }
    }

    public String uploadImageToShelterPet(Long shelterPetID, MultipartFile imageFile) {

        ShelterPet shelterPet = shelterPetService.fetchShelterPetByID(shelterPetID);
        accessValidator.isSheltersManagerOrAdmin(accessValidator.getAuth(), shelterPet.getShelter());

        if (shelterPet.getImage() != null) {
            throw new BadRequestException(
                    "ShelterPet already has an image, for updating use PUT /shelterpets/{shelterPetID}/image"
            );
        }

        Image image = handleImageFile(imageFile);
        image.setShelterPet(shelterPet);
        imageRepository.save(image);

        return "Image Successfully added to ShelterPet: " + shelterPet.getName();
    }

    public String uploadImageToUserPet(Long userPetID, MultipartFile imageFile) {

        UserOwnedPet userOwnedPet = userOwnedPetService.fetchUserOwnedPetById(userPetID);
        accessValidator.isUserOrAdmin(accessValidator.getAuth(), userOwnedPet.getCurrentOwner().getUsername());

        if (userOwnedPet.getImage() != null) {
            throw new BadRequestException(
                    "UserOwnedPet already has an image, for updating use PUT /ownedpets/{userPetID}/image"
            );
        }

        Image image = handleImageFile(imageFile);
        image.setUserOwnedPet(userOwnedPet);
        imageRepository.save(image);

        return "Image Successfully added to UserOwnedPet: " + userOwnedPet.getName();
    }

    public String updateImage(MultipartFile imageFile, Image image) throws BadRequestException {

        validateUpdatePermission(image);

        Image newImage = handleImageFile(imageFile);
        // Copy the new image to the existing image
        ModelMapperHelper.getModelMapper().map(newImage, image);

        imageRepository.save(image);

        return "Image updated successfully: " + imageFile.getOriginalFilename();
    }

    public String deleteImage(Image image) {

        validateUpdatePermission(image);

        Long id = image.getId();
        imageRepository.deleteById(id);

        return "Image with id: " + id + " belonging to " + image.getShelterPet().getName() +  " has been deleted";
    }
}

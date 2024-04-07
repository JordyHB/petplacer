package nl.jordy.petplacer.controllers;

import nl.jordy.petplacer.services.ImageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("images")
@RestController
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    // Posts
    @PostMapping()
    public ResponseEntity<String> uploadImage(@RequestParam("image")MultipartFile imageFile) {
        return ResponseEntity.ok(imageService.uploadImage(imageFile));
    }

    @GetMapping("/{imageID}")
    public ResponseEntity<byte[]> downloadImageByID(@PathVariable Long imageID) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf(MediaType.IMAGE_PNG_VALUE))
                .body(imageService.downloadImage(imageID));
    }
}

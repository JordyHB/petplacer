package nl.jordy.petplacer.helpers;

import nl.jordy.petplacer.interfaces.HasFetchableId;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;

public class BuildUri {

    // Takes in the DTO and builds a URI string for it
    public static URI buildUri(HasFetchableId requestedObject) {
        return URI.create(
                ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/" + requestedObject.getId())
                        .toUriString()
        );
    }
}

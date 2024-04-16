package nl.jordy.petplacer.helpers.modalmapper.converters;

import nl.jordy.petplacer.dtos.summary.ShelterPetSummaryDTO;
import nl.jordy.petplacer.models.Image;
import nl.jordy.petplacer.models.ShelterPet;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

public class ShelterPetToSummaryConverter implements Converter<ShelterPet, ShelterPetSummaryDTO> {

    public String convertToImageLink(Image image) {
        if (image == null) {
            return null;
        }
        return "/images/" + image.getId();
    }

    @Override
    public ShelterPetSummaryDTO convert(MappingContext<ShelterPet, ShelterPetSummaryDTO> context) {
        ShelterPet source = context.getSource();
        ShelterPetSummaryDTO target = context.getDestination();

        if (target == null) {
            target = new ShelterPetSummaryDTO();
        }

        target.setId(source.getId());
        target.setImageLink(convertToImageLink(source.getImage()));


        return target;
    }
}

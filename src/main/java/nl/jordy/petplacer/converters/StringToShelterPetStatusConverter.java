package nl.jordy.petplacer.converters;

import nl.jordy.petplacer.enums.ShelterPetStatus;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToShelterPetStatusConverter implements Converter<String, ShelterPetStatus> {

    @Override
    public ShelterPetStatus convert(String source) {
        try {
            return ShelterPetStatus.valueOf(source.toUpperCase());
        } catch (Exception e) {
            return ShelterPetStatus.INVALID;
        }
    }
}

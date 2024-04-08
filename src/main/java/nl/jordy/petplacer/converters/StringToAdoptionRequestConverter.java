package nl.jordy.petplacer.converters;

import nl.jordy.petplacer.enums.AdoptionRequestStatus;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToAdoptionRequestConverter implements Converter<String, AdoptionRequestStatus> {
        @Override
        public AdoptionRequestStatus convert(String source) {
            try {
                return AdoptionRequestStatus.valueOf(source.toUpperCase());
            } catch (Exception e) {
                return AdoptionRequestStatus.INVALID;
            }
        }
}

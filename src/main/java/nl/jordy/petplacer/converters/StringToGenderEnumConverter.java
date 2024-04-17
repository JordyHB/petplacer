package nl.jordy.petplacer.converters;

import nl.jordy.petplacer.enums.GenderEnum;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToGenderEnumConverter implements Converter<String, GenderEnum> {

    @Override
    public GenderEnum convert(String source) {
        try {
            return GenderEnum.valueOf(source.toUpperCase());
        } catch (Exception e) {
            return GenderEnum.INVALID;
        }
    }
}

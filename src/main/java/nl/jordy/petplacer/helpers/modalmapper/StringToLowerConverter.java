package nl.jordy.petplacer.helpers.modalmapper;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

public class StringToLowerConverter implements Converter<String, String> {

    @Override
    public String convert(MappingContext<String, String> context) {
        String source = context.getSource();
        return source == null ? null : source.toLowerCase();
    }
}


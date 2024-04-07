package nl.jordy.petplacer.helpers.modalmapper.converters;

import nl.jordy.petplacer.models.Image;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

public class ImageToImageLinkConverter implements Converter<Image, String> {

    @Override
    public String convert(MappingContext<Image, String> context) {
        Image source = context.getSource();
        return source == null ? null : String.format("/images/%s", source.getId());
    }
}

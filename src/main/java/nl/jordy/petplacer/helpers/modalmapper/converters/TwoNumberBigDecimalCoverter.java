package nl.jordy.petplacer.helpers.modalmapper.converters;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class TwoNumberBigDecimalCoverter implements Converter<BigDecimal, BigDecimal> {

    @Override
    public BigDecimal convert(MappingContext<BigDecimal, BigDecimal> context) {
        BigDecimal source = context.getSource();
        return source == null ? null : source.setScale(2, RoundingMode.HALF_UP);
    }
}

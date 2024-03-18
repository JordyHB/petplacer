package nl.jordy.petplacer.helpers;

import nl.jordy.petplacer.exceptions.BadRequestException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.validation.BindingResult;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CheckBindingResultTest {

    @DisplayName("Throws a bad request exception on error")
    @Test
    public void testThrowBadRequestException() {
        BindingResult mockBindingResult = mock(BindingResult.class);
        when(mockBindingResult.hasErrors()).thenReturn(true);

        assertThrows(BadRequestException.class, () -> CheckBindingResult.checkBindingResult(mockBindingResult));
    }

    @DisplayName("Doesn't throw exception when there is no error")
    @Test
    public void TestNoException() {
        BindingResult mockBindingResult = mock(BindingResult.class);
        when(mockBindingResult.hasErrors()).thenReturn(false);

        CheckBindingResult.checkBindingResult(mockBindingResult);
    }
}
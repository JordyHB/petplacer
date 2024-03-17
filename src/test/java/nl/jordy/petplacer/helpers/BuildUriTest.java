package nl.jordy.petplacer.helpers;

import nl.jordy.petplacer.interfaces.HasFetchableId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BuildUriTest {

    private HasFetchableId mockObject;

    @BeforeEach
    public void setup() {
        mockObject = mock(HasFetchableId.class);
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

    @Test
    public void buildUri_returnsCorrectUri_whenIdIsPresent() {
        when(mockObject.getId()).thenReturn(12L);

        URI expectedUri = URI.create("http://localhost/12");
        URI actualUri = BuildUri.buildUri(mockObject);

        assertEquals(expectedUri, actualUri);
    }

    @Test
    public void buildUri_returnsCorrectUri_whenIdIsNull() {
        when(mockObject.getId()).thenReturn(null);

        URI expectedUri = URI.create("http://localhost/null");
        URI actualUri = BuildUri.buildUri(mockObject);

        assertEquals(expectedUri, actualUri);
    }
}
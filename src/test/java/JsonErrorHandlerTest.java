import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.bsinfo.rest.JsonErrorHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class JsonErrorHandlerTest {

    @Test
    public void testJsonErrorHandler() {
        JsonProcessingException exception = null;
        try {
            new ObjectMapper().readValue("", LocalDate.class);
        } catch (JsonProcessingException e) {
            exception = e;
        }

        Assertions.assertNotNull(exception);

        try (var response = new JsonErrorHandler().toResponse(exception)) {
            Assertions.assertEquals(400, response.getStatus());
            Assertions.assertEquals(exception.getMessage(), response.getEntity());
        }
    }
}

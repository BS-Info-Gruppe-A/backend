import com.fasterxml.jackson.core.JsonProcessingException;
import eu.bsinfo.rest.JsonSerializer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class JsonSerializerTest {

    @Test
    public void testJsonDateFormatting() throws JsonProcessingException {
        var serializer = new JsonSerializer();
        var mapper = serializer.getContext(LocalDate.class);

        var json = mapper.writeValueAsString(LocalDate.of(2022, 11, 23));

        Assertions.assertEquals("\"2022-11-23\"", json);
    }
}

package eu.bsinfo.rest;

import eu.bsinfo.Backend;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

@Path("setupDB")
public class SetupDBHandler {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response setupDB(@DefaultValue("false") @QueryParam("seed") boolean seed) throws SQLException, IOException {
        var databaseManager = Backend.getInstance().getDatabaseManager();

        try (var connection = databaseManager.getConnection();
             var statement = connection.createStatement()) {
            statement.execute("DROP TABLE IF EXISTS readings");
            statement.execute("DROP TABLE IF EXISTS customers");
            statement.execute("DROP TYPE IF EXISTS gender");
            statement.execute("DROP TYPE IF EXISTS metertype");

            try (var schema = new BufferedInputStream(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream("schema.sql")))) {
                var text = new String(schema.readAllBytes());

                statement.execute(text);
            }

            if (seed) {
                try (var schema = new BufferedInputStream(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream("seed.sql")))) {
                    var text = new String(schema.readAllBytes());

                    statement.execute(text);
                }
            }

        }

        return Response.noContent().build();
    }
}

package eu.bsinfo.rest;

import eu.bsinfo.Backend;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.sql.SQLException;

@Path("setupDB")
public class SetupDBHandler {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response setupDB() throws SQLException, IOException {
        var databaseManager = Backend.getInstance().getDatabaseManager();

        try (var connection = databaseManager.getConnection();
             var statement = connection.createStatement()) {
            statement.execute("DROP TABLE IF EXISTS readings");
            statement.execute("DROP TABLE IF EXISTS customers");
            statement.execute("DROP TYPE IF EXISTS gender");
            statement.execute("DROP TYPE IF EXISTS metertype");

            try (var schema = new BufferedInputStream(ClassLoader.getSystemResourceAsStream("schema.sql"))) {
                var text = new String(schema.readAllBytes());

                statement.execute(text);
            }
        }

        return Response.noContent().build();
    }
}

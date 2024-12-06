package eu.bsinfo.rest;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.ext.Provider;

@Provider
public class CORSHandler implements ContainerResponseFilter {
    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) {
        MultivaluedMap<String, Object> headers = responseContext.getHeaders();
        var origins = System.getenv("CORS_ORIGIN");
        if (origins != null) {
            var list = origins.split(",\\s*");
            for (var origin : list) {
                if (origin.equals(requestContext.getHeaderString("Origin"))) {
                     headers.add("Access-Control-Allow-Origin", origin);
                }
            }
        }
        headers.add("Access-Control-Allow-Headers", "Accept, Content-Type");
        headers.add("Access-Control-Allow-Credentials", "true");
        headers.add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        headers.add("Vary", "Origin");
    }
}

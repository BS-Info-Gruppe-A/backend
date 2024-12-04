package eu.bsinfo.rest;

import jakarta.ws.rs.ext.ParamConverter;
import jakarta.ws.rs.ext.ParamConverterProvider;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.time.LocalDate;

public class LocalDateParamConverter implements ParamConverter<LocalDate> {
    @Override
    public LocalDate fromString(String s) {
        return LocalDate.from(JsonSerializer.FORMAT.parse(s));
    }

    @Override
    public String toString(LocalDate date) {
        return JsonSerializer.FORMAT.format(date);
    }

    @jakarta.ws.rs.ext.Provider
    public static class Provider implements ParamConverterProvider {

        @SuppressWarnings("unchecked")
        @Override
        public <T> ParamConverter<T> getConverter(Class<T> aClass, Type type, Annotation[] annotations) {
            if (aClass.equals(LocalDate.class)) {
                return (ParamConverter<T>) new LocalDateParamConverter();
            }

            return null;
        }
    }
}

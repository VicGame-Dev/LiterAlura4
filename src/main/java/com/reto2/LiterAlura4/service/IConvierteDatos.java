package com.reto2.LiterAlura4.service;

public interface IConvierteDatos {
    <T> T obtenerDatos(String json, Class<T> clase);
}

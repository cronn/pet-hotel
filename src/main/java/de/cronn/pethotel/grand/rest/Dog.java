package de.cronn.pethotel.grand.rest;

public record Dog(long id, String name, long chipNumber, long ownerId) implements Pet {}

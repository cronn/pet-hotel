package de.cronn.pethotel.classic.rest;

import jakarta.validation.constraints.NotNull;

public record CheckinPetData(@NotNull String name, Long chipNumber, @NotNull PetType type) {}

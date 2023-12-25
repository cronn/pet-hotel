package de.cronn.pethotel.grand.rest;

import jakarta.validation.constraints.NotNull;

public record CheckinFishData(@NotNull String name) implements CheckinPetData {}

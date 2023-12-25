package de.cronn.pethotel.classic.rest;

import jakarta.validation.constraints.NotNull;
import java.util.List;

public record GetPetsResponse(@NotNull List<Pet> pets) {}

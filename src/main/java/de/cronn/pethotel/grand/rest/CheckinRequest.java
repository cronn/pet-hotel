package de.cronn.pethotel.grand.rest;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record CheckinRequest(
    @NotNull @NotEmpty List<CheckinPetData> pets, @NotNull OwnerData person) {}

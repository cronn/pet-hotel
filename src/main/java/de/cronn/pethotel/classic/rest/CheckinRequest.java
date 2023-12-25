package de.cronn.pethotel.classic.rest;

import java.util.List;

public record CheckinRequest(List<CheckinPetData> pets) {}

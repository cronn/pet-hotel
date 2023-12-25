package de.cronn.pethotel.grand.rest;

import java.util.List;

public record CheckinRequest(List<CheckinPetData> pets) {}

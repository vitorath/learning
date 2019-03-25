package app.ws.service;

import app.ws.shared.dto.AddressDto;

import java.util.List;

public interface AddressService {
	List<AddressDto> getAddresses(String userId);
	AddressDto getAddress(String addressId);
}

package app.ws.service.impl;

import app.ws.io.entity.AddressEntity;
import app.ws.io.entity.UserEntity;
import app.ws.io.repositories.AddressRepository;
import app.ws.io.repositories.UserRepository;
import app.ws.service.AddressService;
import app.ws.shared.dto.AddressDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressService{

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	AddressRepository addressRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Override
	public List<AddressDto> getAddresses(String userId) {
		List<AddressDto> returnValue = new ArrayList<>();
		
		UserEntity userEntity = userRepository.findByUserId(userId);
		
		if (userEntity == null ) return returnValue;
		
		Iterable<AddressEntity> addresses = addressRepository.findAllByUserDetails(userEntity);
		addresses.forEach(addressEntity -> {
			returnValue.add(modelMapper.map(addressEntity, AddressDto.class));
		});
		return returnValue;
	}

	@Override
	public AddressDto getAddress(String addressId) {
		AddressEntity addressEntity = addressRepository.findByAddressId(addressId);
		if (addressEntity != null) {
			return modelMapper.map(addressEntity, AddressDto.class);
		}
		return null;
	}

}

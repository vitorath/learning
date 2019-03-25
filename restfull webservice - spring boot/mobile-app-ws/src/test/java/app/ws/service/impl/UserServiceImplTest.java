package app.ws.service.impl;

import app.ws.exceptions.UserServiceException;
import app.ws.io.entity.AddressEntity;
import app.ws.io.entity.UserEntity;
import app.ws.io.repositories.UserRepository;
import app.ws.shared.AmazonSES;
import app.ws.shared.Utils;
import app.ws.shared.dto.AddressDto;
import app.ws.shared.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

	@InjectMocks
	UserServiceImpl userService;
	
	@Mock
	UserRepository userRepository;

	@Mock
	AmazonSES amazonSES;

	@Mock
	Utils utils;
	
	@Mock
	BCryptPasswordEncoder bCryptPasswordEncoder;

	String userId = "sdffgl4fsdf";
	String encryptedPassword = "74sdfsd5754dfsd1sdf";

	UserEntity userEntity;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		userEntity = new UserEntity();
		userEntity.setId(1L);
		userEntity.setFirstName("Vitor");
		userEntity.setLastName("Thomazini");
		userEntity.setEmail("vitor.a.th@test.com");
		userEntity.setEncryptedPassword(encryptedPassword);
		userEntity.setUserId(userId);
		userEntity.setAddresses(getAddressesEntity());
	}

	@Test
	void testGetUser() {

		when(userRepository.findByEmail(anyString())).thenReturn(userEntity);
		
		UserDto userDto = userService.getUser("vitor.a.th@test.com");
		assertNotNull(userDto);
		assertEquals("Vitor", userDto.getFirstName());
	}

	@Test
	void testUsernameNotFoundException() {
		when(userRepository.findByEmail(anyString())).thenReturn(null);
		assertThrows(UsernameNotFoundException.class, () -> {
			userService.getUser("vitor.a.th@test.com");
		});
	}

	@Test
	void testCreateUser_CreaterUserServiceException() {
		when(userRepository.findByEmail(anyString())).thenReturn(userEntity);
		UserDto userDto = new UserDto();
		userDto.setFirstName("Vitor");
		userDto.setLastName("Thomazini");
		userDto.setPassword("123456");
		userDto.setEmail("vitor.a.th@test.com");
		userDto.setAddresses(getAddressesDto());

		assertThrows(UserServiceException.class, () ->{
			userService.createUser(userDto);
		});
	}

	@Test
	void testCreateUser() {
		when(userRepository.findByEmail(anyString())).thenReturn(null);
		when(utils.generateAddressId(anyInt())).thenReturn("SDFJJGHdfjsdjf2655");
		when(utils.generateUserId(anyInt())).thenReturn(userId);
		when(bCryptPasswordEncoder.encode(anyString())).thenReturn(encryptedPassword);
		when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);
		doNothing().when(amazonSES).verifyEmail(any(UserDto.class));

		UserDto userDto = new UserDto();
		userDto.setFirstName("Vitor");
		userDto.setLastName("Thomazini");
		userDto.setPassword("123456");
		userDto.setEmail("vitor.a.th@test.com");
		userDto.setAddresses(getAddressesDto());

		UserDto storedUserDetails = userService.createUser(userDto);

		assertNotNull(storedUserDetails);
		assertEquals(userEntity.getFirstName(), storedUserDetails.getFirstName());
		assertEquals(userEntity.getLastName(), storedUserDetails.getLastName());
		assertNotNull(storedUserDetails.getUserId());
		assertEquals(storedUserDetails.getAddresses().size(), userEntity.getAddresses().size());

		verify(utils, times(2)).generateAddressId(30);
		verify(bCryptPasswordEncoder, times(1)).encode("123456");
		verify(userRepository, times(1)).save(any(UserEntity.class));
	}

	@Test
	void testCreateUserUserServiceException(){
		when(userRepository.findByEmail(anyString())).thenReturn(userEntity);

		UserDto userDto = new UserDto();
		userDto.setFirstName("Vitor");
		userDto.setLastName("Thomazini");
		userDto.setPassword("123456");
		userDto.setEmail("vitor.a.th@test.com");

		assertThrows(UserServiceException.class, () -> {
			userService.createUser(userDto);
		});

	}

	private List<AddressDto> getAddressesDto() {
		AddressDto shippingAddressDto = new AddressDto();
		shippingAddressDto.setType("shipping");
		shippingAddressDto.setCity("Vancouver");
		shippingAddressDto.setCountry("Canada");
		shippingAddressDto.setPostalCode("ABC123");
		shippingAddressDto.setStreetName("123 Street name");

		AddressDto billingAddressDto = new AddressDto();
		billingAddressDto.setType("billing");
		billingAddressDto.setCity("Vancouver");
		billingAddressDto.setCountry("Canada");
		billingAddressDto.setPostalCode("ABC123");
		billingAddressDto.setStreetName("123 Street name");

		List<AddressDto> addressesDto = new ArrayList<>();
		addressesDto.add(shippingAddressDto);
		addressesDto.add(billingAddressDto);

		return addressesDto;
	}

	private List<AddressEntity> getAddressesEntity() {
		List<AddressDto> addressesDto = getAddressesDto();
		Type listType = new TypeToken<List<AddressEntity>>() {}.getType();
		return new ModelMapper().map(addressesDto, listType);
	}

}

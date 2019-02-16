package app.ws.ui.controller;

import app.ws.service.impl.UserServiceImpl;
import app.ws.shared.dto.AddressDto;
import app.ws.shared.dto.UserDto;
import app.ws.ui.model.response.UserRest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class UserControllerTest {

    final String USER_ID = "dsjkfhjsdfgksl54dsf";

    @InjectMocks
    UserController userController;

    @Mock
    UserServiceImpl userService;

    UserDto userDto;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        userDto = new UserDto();
        userDto.setFirstName("Vitor");
        userDto.setLastName("Thomazini");
        userDto.setPassword("123456");
        userDto.setUserId(USER_ID);
        userDto.setEmail("vitor.a.th@test.com");
        userDto.setAddresses(getAddressesDto());

    }

    @Test
    void getUser() {
        when(userService.getUserByUserId(anyString())).thenReturn(userDto);
        UserRest userRest = userController.getUser(USER_ID);

        assertNotNull(userRest);
        assertEquals(userRest.getUserId(), userRest.getUserId());
        assertEquals(userRest.getFirstName(), userDto.getFirstName());
        assertEquals(userRest.getLastName(), userDto.getLastName());
        assertTrue(userRest.getAddresses().size() == userDto.getAddresses().size());
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
}
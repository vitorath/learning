package app.ws.ui.controller;

import app.ws.exceptions.UserServiceException;
import app.ws.service.AddressService;
import app.ws.service.UserService;
import app.ws.shared.dto.AddressDto;
import app.ws.shared.dto.UserDto;
import app.ws.ui.model.request.PasswordResetModel;
import app.ws.ui.model.request.PasswordResetRequestModel;
import app.ws.ui.model.request.UserDetailsRequestModel;
import app.ws.ui.model.response.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	UserService userService;
	
	@Autowired
	AddressService addressService;
	
	@Autowired
	ModelMapper modelMapper;
	
	@GetMapping(path="/{id}", 
			produces= {  MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public UserRest getUser(@PathVariable("id") String id) {
		ModelMapper modelMapper = new ModelMapper();
		UserDto userDto = userService.getUserByUserId(id);
		UserRest returnValue = modelMapper.map(userDto, UserRest.class);
		
		return returnValue;
	}
	
	@PostMapping(
			consumes={ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE } , 
			produces= { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails) {
		
		if (userDetails.getFirstName().isEmpty())
			throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
		
		UserDto userDto = modelMapper.map(userDetails, UserDto.class);
		
		UserDto createdUser = userService.createUser(userDto);
		UserRest returnValue = modelMapper.map(createdUser, UserRest.class);
		
		return returnValue;
	}
	
	@PutMapping(path="/{id}",
			consumes={ MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE } , 
			produces= { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public UserRest updateUser(@PathVariable String id, @RequestBody UserDetailsRequestModel userDetails) {
		
		if (userDetails.getFirstName().isEmpty())
			throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
		
		UserDto userDto = modelMapper.map(userDetails, UserDto.class);
		
		UserDto updateUser = userService.updateUser(id,userDto);
		UserRest returnValue = modelMapper.map(updateUser, UserRest.class);
		
		return returnValue;

	}
	
	@DeleteMapping(path="/{id}", produces= { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public OperationStatusModel deleteUser(@PathVariable String id) {
		OperationStatusModel returnValue = new OperationStatusModel();
		returnValue.setOperationName(RequestOperationName.DELETE.name());
		
		userService.deleteUser(id);
		
		returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
		return returnValue;
	}
	
	@GetMapping(produces= {  MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public List<UserRest> getUsers(@RequestParam(value = "page", defaultValue = "1") int page, @RequestParam(value = "limit", defaultValue = "25") int limit) {
		List<UserRest> returnValue = new ArrayList<>();
		List<UserDto> users = userService.getUsers(page, limit);
		
		users.forEach(userDto -> {
			UserRest userModel = modelMapper.map(userDto, UserRest.class);
			returnValue.add(userModel);			
		});
		
		return returnValue;
	}
	
	@GetMapping(path="/{id}/addresses", 
			produces= {  MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, "application/hal+json" })
	public Resources<AddressRest> getUserAddresses(@PathVariable("id") String id) {
		List<AddressDto> addressesDto = addressService.getAddresses(id);
		
		if (addressesDto != null && !addressesDto.isEmpty()) {
			Type listType = new TypeToken<List<AddressRest>>() {}.getType();
			List<AddressRest> addressesListRestModel = modelMapper.map(addressesDto, listType);	
			
			addressesListRestModel.forEach(addressRest -> {
				Link addressLink = linkTo(methodOn(UserController.class).getUserAddress(id, addressRest.getAddressId())).withSelfRel();
				addressRest.add(addressLink);

				Link userLink = linkTo(methodOn(UserController.class).getUser(id)).withRel("user");
				addressRest.add(userLink);	
			});
			
			return new Resources<>(addressesListRestModel);
		}
		
		return new Resources<>(Collections.emptyList());
	}
	
	@GetMapping(path="/{userId}/addresses/{addressId}", 
			produces= {  MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, "application/hal+json" })
	public Resource<AddressRest> getUserAddress(@PathVariable("userId") String userId, @PathVariable("addressId") String addressId) {
		AddressDto addressDto = addressService.getAddress(addressId);
		
		Link addressLink = linkTo(methodOn(UserController.class).getUserAddress(userId, addressId)).withSelfRel();
		Link userLink = linkTo(methodOn(UserController.class).getUser(userId)).withRel("user");
		Link addressesLink = linkTo(methodOn(UserController.class).getUserAddresses(userId)).withRel("addresses");
		
		AddressRest addressRestModel = modelMapper.map(addressDto, AddressRest.class);
		addressRestModel.add(addressLink);
		addressRestModel.add(userLink);
		addressRestModel.add(addressesLink);
		
		return new Resource<>(addressRestModel);
	}

	@GetMapping(path = "/email-verification", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public OperationStatusModel verifyEmailToken(@RequestParam(value = "token") String token) {
		OperationStatusModel returnValue = new OperationStatusModel();
		returnValue.setOperationName(RequestOperationName.VERIFY_EMAIL.name());

		boolean isVerified = userService.verifyEmailToken(token);

		if (isVerified) {
			returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
		} else {
			returnValue.setOperationResult(RequestOperationStatus.ERROR.name());
		}

		return returnValue;
	}

	@PostMapping(path = "/password-reset-request",
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
			consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public OperationStatusModel requestReset(@RequestBody PasswordResetRequestModel passwordResetRequestModel) {
		OperationStatusModel returnValue = new OperationStatusModel();

		boolean operationResult = userService.requestPasswordReset(passwordResetRequestModel.getEmail());

		returnValue.setOperationName(RequestOperationName.REQUEST_PASSWORD_RESET.name());
		returnValue.setOperationResult(RequestOperationStatus.ERROR.name());

		if (operationResult) {
			returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
		}

		return returnValue;
	}

	@PostMapping(path = "/password-reset",
			consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public OperationStatusModel resetPassword(@RequestBody PasswordResetModel passwordResetModel) {
		OperationStatusModel returnValue = new OperationStatusModel();

		boolean  operationResult = userService.resetPassword(passwordResetModel.getToken(),
				passwordResetModel.getPassword());

		returnValue.setOperationName(RequestOperationName.PASSWORD_RESET.name());
		returnValue.setOperationResult(RequestOperationStatus.ERROR.name());

		if (operationResult) {
			returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
		}
		return  returnValue;
	}

}

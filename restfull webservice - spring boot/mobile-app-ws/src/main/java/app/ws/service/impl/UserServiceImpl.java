package app.ws.service.impl;

import java.util.ArrayList;
import java.util.List;

import app.ws.shared.AmazonSES;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import app.ws.exceptions.UserServiceException;
import app.ws.io.entity.UserEntity;
import app.ws.io.repositories.UserRepository;
import app.ws.service.UserService;
import app.ws.shared.Utils;
import app.ws.shared.dto.UserDto;
import app.ws.ui.model.response.ErrorMessages;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	Utils utils;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Override
	public UserDto createUser(UserDto user) {
		
		if (userRepository.findByEmail(user.getEmail()) != null)
			throw new UserServiceException("Record already exists");
		
		user.getAddresses().forEach(address -> {
			address.setUserDetails(user);
			address.setAddressId(utils.generateAddressId(30));
		});

		ModelMapper modelMapper = new ModelMapper();
		UserEntity userEntity = modelMapper.map(user, UserEntity.class);
		
		String publicUserId = utils.generateUserId(30);
		userEntity.setUserId( publicUserId );
		userEntity.setEncryptedPassword( bCryptPasswordEncoder.encode( user.getPassword() ) );
		userEntity.setEmailVerificationToken( utils.generateEmailVerificationToken( publicUserId ) );
		userEntity.setEmailVerificationStatus( false );

		UserEntity storedUserDetails = userRepository.save(userEntity);
		UserDto returnValue = modelMapper.map(storedUserDetails, UserDto.class);

		new AmazonSES().verifyEmail(returnValue);

		return returnValue;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		UserEntity userEntity = userRepository.findByEmail(email);
		if (userEntity == null) throw new UsernameNotFoundException(email);
		return new User( userEntity.getEmail(),  userEntity.getEncryptedPassword(),
				userEntity.getEmailVerificationStatus(),
				true, true, true, new ArrayList<>() );
	}

	@Override
	public UserDto getUser(String email) {
		
		UserEntity userEntity = userRepository.findByEmail(email);
		if (userEntity == null) throw new UsernameNotFoundException(email);
		
		UserDto returnValue = new UserDto();
		BeanUtils.copyProperties(userEntity, returnValue);
		
		return returnValue;
	}

	@Override
	public UserDto getUserByUserId(String userId) {
		
		UserDto returnValue = new UserDto();
		UserEntity userEntity = userRepository.findByUserId(userId);
		
		if (userEntity == null) 
			throw new UsernameNotFoundException("User with ID: " + userId + " not found");
		
		BeanUtils.copyProperties(userEntity, returnValue);
		return returnValue;
	}

	@Override
	public UserDto updateUser(String userId, UserDto user) {
		UserDto returnValue = new UserDto();
		UserEntity userEntity = userRepository.findByUserId(userId);
		
		if (userEntity == null) 
			throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
		
		userEntity.setFirstName(user.getFirstName());
		userEntity.setLastName(user.getLastName());
		
		UserEntity updatedUSerDetails = userRepository.save(userEntity);
		BeanUtils.copyProperties(updatedUSerDetails, returnValue);
		
		return returnValue;
	}

	@Override
	public void deleteUser(String userId) {
		UserEntity userEntity = userRepository.findByUserId(userId);
		
		if (userEntity == null) 
			throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
		
		userRepository.delete(userEntity);	
	}

	@Override
	public List<UserDto> getUsers(int page, int limit) {
		List<UserDto> returnValue = new ArrayList<>();
		PageRequest pageableRequest = PageRequest.of(page, limit);
		
		if (page > 0 ) page -= 1;
		
		Page<UserEntity> usersPage = userRepository.findAll(pageableRequest);
		List<UserEntity> users = usersPage.getContent();
		
		users.forEach(userEntity -> {
			UserDto userDto = new UserDto();
			BeanUtils.copyProperties(userEntity, userDto);
			returnValue.add(userDto);
		});
		
		return returnValue;
	}

	@Override
	public boolean verifyEmailToken(String token) {
		boolean returnValue = false;

		UserEntity userEntity = userRepository.findUserByEmailVerificationToken(token);
		if (userEntity != null) {
			boolean hasTokenExpired = Utils.hasTokenExpired(token);
			if (!hasTokenExpired) {
				userEntity.setEmailVerificationToken(null);
				userEntity.setEmailVerificationStatus(Boolean.TRUE);
				userRepository.save(userEntity);
				returnValue = true;
			}
		}
		return returnValue;
	}

}

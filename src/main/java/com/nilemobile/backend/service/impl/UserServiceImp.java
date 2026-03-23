package com.nilemobile.backend.service.impl;

import com.nilemobile.backend.config.JwtProvider;
import com.nilemobile.backend.exception.EmailAlreadyExisted;
import com.nilemobile.backend.exception.ErrorCode;
import com.nilemobile.backend.exception.PhoneNumberAlreadyExisted;
import com.nilemobile.backend.mapper.UserMapper;
import com.nilemobile.backend.model.Customer;
import com.nilemobile.backend.model.User;
import com.nilemobile.backend.reponse.RegisterResponseDTO;
import com.nilemobile.backend.reponse.UserProfileDTO;
import com.nilemobile.backend.repository.CustomerRepository;
import com.nilemobile.backend.repository.UserRepository;
import com.nilemobile.backend.request.ChangePasswordRequest;
import com.nilemobile.backend.request.CreateUserRequest;
import com.nilemobile.backend.service.UserException;
import com.nilemobile.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;


    @Override
    @Transactional
    public RegisterResponseDTO registerUser(CreateUserRequest request) throws UserException {

        Optional<User> existingUserByEmail = userRepository.findByEmail(request.getPhoneNumber());
        if (existingUserByEmail.isPresent()) {
            throw new EmailAlreadyExisted(ErrorCode.EMAIL_ALREADY_EXISTS.getMessage());
        }

        Optional<User> existingUserByPhoneNumber = userRepository.findByPhoneNumber(request.getPhoneNumber());
        if (existingUserByPhoneNumber.isPresent()) {
            throw new PhoneNumberAlreadyExisted(ErrorCode.PHONE_NUMBER_ALREADY_EXISTS.getMessage());
        }

        // Map request to User entity (password ignored in mapper)
        User newUser = userMapper.toEntity(request);
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        User savedUser = userRepository.save(newUser);

        // Create associated Customer entity
        Customer newCustomer = new Customer();
        newCustomer.setFirstName(request.getFirstName());
        newCustomer.setLastName(request.getLastName());
        newCustomer.setUser(savedUser);
        Customer savedCustomer = customerRepository.save(newCustomer);

        // Build response DTO
        RegisterResponseDTO response = new RegisterResponseDTO();
        response.setUserId(savedUser.getUserId());
        response.setFirstName(savedCustomer.getFirstName());
        response.setLastName(savedCustomer.getLastName());
        response.setEmail(savedUser.getEmail());
        response.setPhoneNumber(savedUser.getPhoneNumber());

        return response;
    }

    @Override
    public User findUserById(Long userID) throws UserException {
        Optional<User> user = userRepository.findById(userID);
        if (user.isPresent()) {
            return user.get();
        }
        throw new UserException("User not found with user_id: " + userID);
    }

    @Override
    public User findUserProfileByJwt(String jwt) throws UserException {
        String phoneNumber = jwtProvider.getPhoneNumberfromToken(jwt);

        User user = userRepository.findByPhoneNumber(phoneNumber);

        if (user == null) {
            throw new UserException("User not found with email: " + phoneNumber);
        }
        return user;
    }

    @Override
    public UserProfileDTO updateProfile(Long userId, User user) throws UserException {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();
            existingUser.setEmail(user.getEmail());
            existingUser.setPhoneNumber(user.getPhoneNumber());

            User updatedUser = userRepository.save(existingUser);
            
            // Get customer info if exists
            Customer customer = existingUser.getCustomer();
            return new UserProfileDTO(
                    updatedUser.getUserId(),
                    customer != null ? customer.getFirstName() : "",
                    customer != null ? customer.getLastName() : "",
                    updatedUser.getEmail(),
                    updatedUser.getPhoneNumber()
            );
        } else {
            throw new UserException("User not found");
        }
    }

    @Override
    public User updateProfileUser(Long userId, UserProfileDTO userProfileDTO) throws UserException {
        if(userProfileDTO.getFirstName()==null || userProfileDTO.getFirstName().isEmpty() ||
                userProfileDTO.getLastName()==null || userProfileDTO.getLastName().isEmpty() ||
                userProfileDTO.getEmail()==null || userProfileDTO.getEmail().isEmpty() ||
                userProfileDTO.getPhoneNumber()==null || userProfileDTO.getPhoneNumber().isEmpty()){
            throw new UserException("User profile information is incomplete");
        }
        User user = findUserById(userId);
        user.setEmail(userProfileDTO.getEmail());
        user.setPhoneNumber(userProfileDTO.getPhoneNumber());
        
        // Update Customer if exists
        if (user.getCustomer() != null) {
            user.getCustomer().setFirstName(userProfileDTO.getFirstName());
            user.getCustomer().setLastName(userProfileDTO.getLastName());
            customerRepository.save(user.getCustomer());
        }
        
        return userRepository.save(user);
    }

    @Override
    public User findByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber);
    }

    @Override
    public void changePassword(String phoneNumber, ChangePasswordRequest request) throws Exception {
        // Tìm user theo phoneNumber
        User user = userRepository.findByPhoneNumber(phoneNumber);
        if (user == null) {
            throw new Exception("User not found with phone number: " + phoneNumber);
        }

        // Kiểm tra mật khẩu cũ
        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new Exception("Old password is incorrect");
        }

        // Kiểm tra mật khẩu mới và xác nhận mật khẩu có khớp không
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new Exception("New password and confirm password do not match");
        }

        // Kiểm tra mật khẩu mới có hợp lệ không (ví dụ: độ dài tối thiểu)
        if (request.getNewPassword().length() < 8) {
            throw new Exception("New password must be at least 8 characters long");
        }

        // Mã hóa mật khẩu mới và cập nhật vào cơ sở dữ liệu
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }
}

package lk.ijse.userservice.service.impl;

import lk.ijse.userservice.dto.UserDTO;
import lk.ijse.userservice.entity.User;
import lk.ijse.userservice.repo.UserRepository;
import lk.ijse.userservice.service.UserService;
import lk.ijse.userservice.util.VarList;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class UserServiceImpl implements UserDetailsService, UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), getAuthority(user));
    }

    public UserDTO loadUserDetailsByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        return modelMapper.map(user,UserDTO.class);
    }

    private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole()));
        return authorities;
    }

    @Override
    public int saveUser(UserDTO userDTO) {
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            return VarList.Not_Acceptable;
        } else {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
//            userDTO.setRole("USER");
            userRepository.save(modelMapper.map(userDTO, User.class));
            return VarList.Created;
        }
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDTO> userDTOList = new ArrayList<>();

        for (User user : users) {
            UserDTO dto = modelMapper.map(user, UserDTO.class);
            userDTOList.add(dto);
        }

        return userDTOList;
    }

    @Override
    public UserDTO updateUser(UUID id, UserDTO dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Update fields
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setRole(dto.getRole());

        // Save updated entity
        User updatedUser = userRepository.save(user);

        // Convert updated entity to DTO
        UserDTO updatedDTO = modelMapper.map(updatedUser, UserDTO.class);
        return updatedDTO;
    }

    @Override
    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }
}

package lk.ijse.userservice.service;

import lk.ijse.userservice.dto.UserDTO;

import java.util.List;
import java.util.UUID;

public interface UserService {
    int saveUser(UserDTO userDTO);
    List<UserDTO> getAllUsers();
    UserDTO updateUser(UUID id, UserDTO dto);
    void deleteUser(UUID id);
    UserDTO loadUserDetailsByUsername(String username);
}

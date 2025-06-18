package lk.ijse.userservice.controller;

import jakarta.servlet.http.HttpServletRequest;
import lk.ijse.userservice.dto.AuthDTO;
import lk.ijse.userservice.dto.ResponseDTO;
import lk.ijse.userservice.dto.UserDTO;
import lk.ijse.userservice.service.UserService;
import lk.ijse.userservice.util.JwtUtil;
import lk.ijse.userservice.util.VarList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/user")
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    public UserController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> registerUser(@RequestBody UserDTO userDTO) {
        try {
            int res = userService.saveUser(userDTO);
            switch (res) {
                case VarList.Created -> {
                    String token = jwtUtil.generateToken(userDTO);
                    AuthDTO authDTO = new AuthDTO(userDTO.getEmail(), token);
                    return ResponseEntity.status(HttpStatus.CREATED)
                            .body(new ResponseDTO(VarList.Created, "User registered successfully", authDTO));
                }
                case VarList.Not_Acceptable -> {
                    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                            .body(new ResponseDTO(VarList.Not_Acceptable, "Email already used", null));
                }
                default -> {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(new ResponseDTO(VarList.Bad_Gateway, "Error occurred", null));
                }
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.Internal_Server_Error, e.getMessage(), null));
        }
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> getAllUsers() {
        List<UserDTO> allUsers = userService.getAllUsers();
        return ResponseEntity.ok(new ResponseDTO(VarList.OK, "User list fetched", allUsers));
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','OWNER')")
    public ResponseEntity<ResponseDTO> updateUser(@PathVariable String id, @RequestBody UserDTO dto) {
        UserDTO updatedUser = userService.updateUser(UUID.fromString(id), dto);
        return ResponseEntity.ok(new ResponseDTO(VarList.OK, "User updated successfully", updatedUser));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDTO> deleteUser(@PathVariable String id) {
        userService.deleteUser(UUID.fromString(id));
        return ResponseEntity.ok(new ResponseDTO(VarList.OK, "User deleted successfully", null));
    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ResponseDTO> getOwnProfile(HttpServletRequest request) {
        String email = (String) request.getAttribute("email");
        UserDTO user = userService.loadUserDetailsByUsername(email);
        return ResponseEntity.ok(new ResponseDTO(VarList.OK, "Current user profile", user));
    }
}

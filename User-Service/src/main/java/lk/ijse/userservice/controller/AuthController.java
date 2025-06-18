package lk.ijse.userservice.controller;

import lk.ijse.userservice.dto.AuthDTO;
import lk.ijse.userservice.dto.ResponseDTO;
import lk.ijse.userservice.dto.UserDTO;
import lk.ijse.userservice.service.impl.UserServiceImpl;
import lk.ijse.userservice.util.JwtUtil;
import lk.ijse.userservice.util.VarList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final UserServiceImpl userService;

    public AuthController(JwtUtil jwtUtil, AuthenticationManager authenticationManager, UserServiceImpl userService) {
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<ResponseDTO> authenticate(@RequestBody UserDTO userDTO) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userDTO.getEmail(), userDTO.getPassword()));

            UserDTO loadedUser = userService.loadUserDetailsByUsername(userDTO.getEmail());
            if (loadedUser == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseDTO(VarList.Not_Found, "User not found", null));
            }

            String token = jwtUtil.generateToken(loadedUser);

            AuthDTO authDTO = new AuthDTO();
            authDTO.setEmail(loadedUser.getEmail());
            authDTO.setToken(token);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDTO(VarList.OK, "Authentication Successful", authDTO));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ResponseDTO(VarList.Unauthorized, "Invalid credentials", e.getMessage()));
        }
    }
}

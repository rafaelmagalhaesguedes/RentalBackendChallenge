package com.rental.controller;

import com.rental.controller.dto.auth.AuthRequest;
import com.rental.controller.dto.auth.ErrorResponse;
import com.rental.controller.dto.auth.TokenResponse;
import com.rental.service.validations.IPasswordValidator;
import com.rental.service.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {
  private final AuthenticationManager authenticationManager;
  private final IPasswordValidator passwordValidator;
  private final TokenService tokenService;

  @Autowired
  public AuthController(AuthenticationManager authenticationManager, IPasswordValidator passwordValidator, TokenService tokenService) {
    this.authenticationManager = authenticationManager;
      this.passwordValidator = passwordValidator;
      this.tokenService = tokenService;
  }

  @PostMapping("/login")
  @Operation(summary = "Authenticate user and generate JWT token")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Successfully authenticated and token generated"),
          @ApiResponse(responseCode = "401", description = "Invalid credentials"),
          @ApiResponse(responseCode = "400", description = "Invalid password format")
  })
  public ResponseEntity<?> login(@RequestBody @Valid AuthRequest req) {
    List<String> errors = passwordValidator.validate(req.password());

    if (!errors.isEmpty()) {
      return ResponseEntity.badRequest().body(new ErrorResponse(errors));
    }

    var user = new UsernamePasswordAuthenticationToken(req.email(), req.password());
    var auth = authenticationManager.authenticate(user);

    var token = tokenService.generateToken(auth.getName());

    return ResponseEntity.ok(new TokenResponse(token));
  }

  @PostMapping("/logout")
  @Operation(summary = "Logout user and clear security context")
  @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successfully logged out") })
  public ResponseEntity<String> logout() {
    SecurityContextHolder.clearContext();
    return ResponseEntity.ok().body("Successfully logged out.");
  }
}

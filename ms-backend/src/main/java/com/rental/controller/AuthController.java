package com.rental.controller;

import com.rental.controller.dto.auth.AuthDto;
import com.rental.controller.dto.auth.TokenDto;
import com.rental.entity.Person;
import com.rental.service.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for authentication.
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

  private final AuthenticationManager authenticationManager;
  private final TokenService tokenService;

  /**
   * Instantiates a new Auth controller.
   *
   * @param authenticationManager the authentication manager
   * @param tokenService          the token service
   */
  @Autowired
  public AuthController(
      AuthenticationManager authenticationManager,
      TokenService tokenService) {
    this.authenticationManager = authenticationManager;
    this.tokenService = tokenService;
  }

  /**
   * Login response entity.
   *
   * @param authDto the auth dto
   * @return the response entity
   */
  @PostMapping("/login")
  @Operation(summary = "User login", description = "Authenticate user and return JWT token")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successful authentication",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = TokenDto.class))),
      @ApiResponse(responseCode = "401", description = "Invalid email or password",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = TokenDto.class)))
  })
  public ResponseEntity<TokenDto> login(@RequestBody AuthDto authDto) {
    try {
      UsernamePasswordAuthenticationToken usernamePassword =
          new UsernamePasswordAuthenticationToken(authDto.email(), authDto.password());

      Authentication auth = authenticationManager.authenticate(usernamePassword);

      String token = tokenService.generateToken((Person) auth.getPrincipal());

      return ResponseEntity.ok(new TokenDto(token));
    } catch (BadCredentialsException ex) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
          .body(new TokenDto("Invalid email or password."));
    }
  }
}

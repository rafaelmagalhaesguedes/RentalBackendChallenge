package com.rental.controller.dto.auth;

import java.util.List;

public record ErrorResponse(List<String> errors){
}

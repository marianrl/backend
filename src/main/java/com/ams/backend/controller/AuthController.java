// package com.ams.backend.controller;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.Authentication;
// import org.springframework.web.bind.annotation.*;

// import com.ams.backend.security.JwtTokenUtil;

// @RestController
// @RequestMapping("/api/v1")
// public class AuthController {

//     @Autowired
//     private JwtTokenUtil jwtTokenUtil;

//     @Autowired
//     private AuthenticationManager authenticationManager;

//     @PostMapping("/authenticate")
//     public ResponseEntity<String> authenticate(@RequestParam String username, @RequestParam String password) {
//         Authentication authentication = authenticationManager.authenticate(
//                 new UsernamePasswordAuthenticationToken(username, password));
//         String token = jwtTokenUtil.generateToken(username);
//         return ResponseEntity.ok(token);
//     }
// }

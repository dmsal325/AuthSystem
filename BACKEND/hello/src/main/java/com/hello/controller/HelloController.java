package com.hello.controller;

import com.hello.service.UserService;
import com.hello.utils.auth.Auth;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static com.hello.utils.DefaultRes.FAIL_DEFAULT_RES;

@RestController
public class HelloController {

    private final UserService userService;

    public HelloController(final UserService userService) {
        this.userService = userService;
    }


    @Auth
    @GetMapping(path="/hello")
    @CrossOrigin
    public ResponseEntity hello(@RequestHeader(value="token") String token) {
        try {
            return new ResponseEntity<>(userService.hello(token), HttpStatus.OK);
        }catch (Exception e) {
//            log.error(e.getMessage());
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}

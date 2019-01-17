package com.devcamp.authsystemserver.controller;

import com.devcamp.authsystemserver.dto.User;
import com.devcamp.authsystemserver.service.UserService;
import com.devcamp.authsystemserver.utils.auth.Auth;
import com.devcamp.authsystemserver.utils.limit.Limit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import static com.devcamp.authsystemserver.utils.DefaultRes.FAIL_DEFAULT_RES;

@Slf4j
@RestController
//@RequestMapping("users")
public class UserController {

    private final UserService userService;

    public UserController(final UserService userService) {
        this.userService = userService;
    }
    //로그인
    @PostMapping(path="/signin")
    @CrossOrigin
    public ResponseEntity signin(@RequestBody final User user) {
        try {
            return new ResponseEntity<>(userService.login(user.getUser_id(), user), HttpStatus.OK);    }catch (Exception e) {
//            log.error(e.getMessage());
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @Auth
    @GetMapping(path="/users")
    @CrossOrigin
    public ResponseEntity getUser(@RequestParam("name") final Optional<String> name) {
        try {
//            //name이 null일 경우 false, null이 아닐 경우 true
////            if(name.isPresent()) {
////                return new ResponseEntity<>(userService.findByName(name.get()), HttpStatus.OK);
////            }
            return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
        }catch (Exception e) {
//            log.error(e.getMessage());
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @PostMapping(path="/signup")
    @CrossOrigin
    public ResponseEntity signup(@RequestBody final User user) {
        try {
            return new ResponseEntity<>(userService.save(user), HttpStatus.OK);
        }catch (Exception e) {
//            log.error(e.getMessage());
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Auth
    @PutMapping(path="/users/{userIdx}")
    public ResponseEntity signUp(
            @PathVariable(value = "userIdx") final int userIdx,
            @RequestBody final User user) {
        try {
            return new ResponseEntity<>(userService.update(userIdx, user), HttpStatus.OK);
        }catch (Exception e) {
//            log.error(e.getMessage());
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Auth
    @Limit
    @DeleteMapping(path="/users/{userIdx}")
    public ResponseEntity deleteUser(@PathVariable(value = "userIdx") final int userIdx) {
        try {
            return new ResponseEntity<>(userService.deleteByUserIdx(userIdx), HttpStatus.OK);
        }catch (Exception e) {
//            log.error(e.getMessage());
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

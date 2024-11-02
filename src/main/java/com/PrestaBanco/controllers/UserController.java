package com.PrestaBanco.controllers;

import com.PrestaBanco.entities.UserEntity;
import com.PrestaBanco.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin("*")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public ResponseEntity<List<UserEntity>> getAllUsers() {
        List<UserEntity> users = userService.getUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserEntity> getUserById(@PathVariable Long id) {
        UserEntity user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/existsByMail/{mail}")
    public ResponseEntity<Boolean> existsByMail(@PathVariable String mail) {
        boolean exists = userService.existsByMail(mail);
        return ResponseEntity.ok(exists);
    }

    @GetMapping("/findByMail/{mail}")
    public ResponseEntity<UserEntity> getUserByMail(@PathVariable String mail) {
        UserEntity user = userService.getUserByMail(mail);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/")
    public ResponseEntity<UserEntity> addUser(@RequestBody UserEntity user) {
        UserEntity userNew = userService.saveUser(user);
        return ResponseEntity.ok(userNew);
    }

    @PutMapping("/")
    public ResponseEntity<UserEntity> updateUser(@RequestBody UserEntity user) {
        UserEntity userUpdated = userService.updateUser(user);
        return ResponseEntity.ok(userUpdated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) throws Exception {
        var isDeleted = userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}


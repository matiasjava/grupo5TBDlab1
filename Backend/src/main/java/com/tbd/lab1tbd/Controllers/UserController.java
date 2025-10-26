package com.tbd.lab1tbd.Controllers;

import com.tbd.lab1tbd.Entities.UserEntity;
import com.tbd.lab1tbd.Services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @PostMapping
    public ResponseEntity<Long> create(@RequestBody UserEntity user) {
        Long id = service.create(user);
        return ResponseEntity.created(URI.create("/api/users/" + id)).body(id);
    }

    @GetMapping("/{id}")
    public UserEntity getbyid(@PathVariable Long id) {
        return service.getbyid(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody UserEntity user) {
        service.update(id, user);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
package com.tbd.lab1tbd.Services;

import com.tbd.lab1tbd.Entities.UserEntity;
import com.tbd.lab1tbd.Repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repo;

    public Long create(UserEntity u) {
        return repo.create(u);
    }

    public UserEntity getbyid(Long id) {
        return repo.getbyid(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    public void update(Long id, UserEntity u) {
        if (repo.update(id, u) == 0) throw new RuntimeException("Usuario no encontrado");
    }

    public void delete(Long id) {
        if (repo.delete(id) == 0) throw new RuntimeException("Usuario no encontrado");
    }
}

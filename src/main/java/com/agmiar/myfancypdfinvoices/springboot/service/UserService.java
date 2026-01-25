package com.agmiar.myfancypdfinvoices.springboot.service;

import com.agmiar.myfancypdfinvoices.springboot.model.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class UserService {

    private CopyOnWriteArrayList<User> users;

    public UserService() {
        this.users = new CopyOnWriteArrayList<>();
    }

    public User create(String name){
        var user = new User(UUID.randomUUID().toString(), name);
        users.add(user);
        return user;
    }

    public User findById(String id){
        for (User user : users) {
            if (user.sameId(id)){
                return user;
            }
        }
        throw new IllegalArgumentException("El ID no pertenece a ningún usuario activo");
    }

    public List<User> findAll(){
        return users;
    }
}

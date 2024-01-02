package com.desafioPicPay.service;

import com.desafioPicPay.domain.user.User;
import com.desafioPicPay.domain.user.UserType;
import com.desafioPicPay.dtos.UserDTO;
import com.desafioPicPay.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public void validateTrasation(User sender, BigDecimal ammount) throws Exception{
        if(sender.getUserType() == UserType.LOJISTA || sender.getUserType() == null){
            throw new Exception("Usuário não tem permissão para efetuar transação");
        }
        if(sender.getBalance().compareTo(ammount) < 0){
            throw new Exception("Saldo insuficiente");
        }
    }

    public User createUser(UserDTO userDTO){
        User user = new User(userDTO);
        this.saveUser(user);
        return user;
    }

    public User findUserById(Long id) throws Exception {
        return this.repository.findUserById(id).orElseThrow(() -> new Exception("Usuário não encotrado"));
    }

    public List<User> getAllUsers(){
        return this.repository.findAll();
    }

    public void saveUser(User user){
        this.repository.save(user);
    }
}

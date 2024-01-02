package com.desafioPicPay.service;

import com.desafioPicPay.domain.transaction.Transaction;
import com.desafioPicPay.domain.user.User;
import com.desafioPicPay.dtos.TransactionDTO;
import com.desafioPicPay.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Service
public class TransactionService {
    @Autowired
    private UserService userService;
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private NotificationService notificationService;

    public Transaction createTransaction(TransactionDTO transactionDTO) throws Exception {
        User sender = this.userService.findUserById(transactionDTO.senderId());
        User receiver = this.userService.findUserById(transactionDTO.receiverId());
        userService.validateTrasation(sender,transactionDTO.value());
        boolean isAuthorized = this.autorizeTransaction(sender,transactionDTO.value());
        if(!isAuthorized){
            throw new Exception("Transação não autorizada");
        };

        Transaction transaction = new Transaction();
        transaction.setAmmount(transactionDTO.value());
        transaction.setSender(sender);
        transaction.setSender(sender);
        transaction.setTimestamp(LocalDateTime.now());

        sender.setBalance(sender.getBalance().subtract(transactionDTO.value()));
        receiver.setBalance(receiver.getBalance().add(transactionDTO.value()));

        this.transactionRepository.save(transaction);
        this.userService.saveUser(sender);
        this.userService.saveUser(receiver);
        this.notificationService.sendNotification(sender,"Transação feita com sucesso!");
        this.notificationService.sendNotification(receiver,"Transação recebida com sucesso!");
        return transaction;
    }

    public boolean autorizeTransaction(User sender, BigDecimal value){
        ResponseEntity<Map> autorizetionResponse = restTemplate.getForEntity("https://run.mocky.io/v3/5794d450-d2e2-4412-8131-73d0293ac1cc", Map.class);
        if(autorizetionResponse.getStatusCode() == HttpStatus.OK && autorizetionResponse.getBody().get("message") == "Autorizado"){
            return true;
        }else return false;
    }
}

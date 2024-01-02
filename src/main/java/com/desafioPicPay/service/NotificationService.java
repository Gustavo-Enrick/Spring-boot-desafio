package com.desafioPicPay.service;

import com.desafioPicPay.domain.user.User;
import com.desafioPicPay.dtos.NotifiquetionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NotificationService {
    private RestTemplate restTemplate;
    public void sendNotification(User user, String message) throws Exception {
        /*String email = user.getEmail();
        NotifiquetionDTO notifiquetionRequest = new NotifiquetionDTO(email,message);
        ResponseEntity <String> notifiquetionResponse = restTemplate.postForEntity("https://run.mocky.io/v3/54dc2cf1-3add-45b5-b5a9-6bf7e7f1f4a6",notifiquetionRequest, String.class);

        if(!(notifiquetionResponse.getStatusCode() == HttpStatus.OK)){
            System.out.println("Erro ao enviar as notificações!");
            throw new Exception("Serviço de notificalções fora do ar");
        }*/
        System.out.println("Notificação enviada para o usuário!");
    }
}

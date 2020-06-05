package com.example.service;

import com.example.bean.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebSocketService {
    //使用SimpMessagingTemplate 向浏览器发送消息
    @Autowired
    private SimpMessagingTemplate template;

    public void sendMessage() throws Exception{
        for(int i=0; i < 10; i++)
        {
            Thread.sleep(1000);
            template.convertAndSend("/topic/getResponse", new Response("Welcome!" + i));
            System.out.println("---Welcome---" + i);
        }
    }

}

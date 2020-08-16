package com.chat.push;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AsynCenter {

    @Async
    public void sendPush(String title, String text, String cid){
        try {
            PushtoApp.send(title, text, cid);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

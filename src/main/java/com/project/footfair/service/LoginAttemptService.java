package com.project.footfair.service;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class LoginAttemptService {
    private static final int MAX_ATTEMPTS = 5;
    private static final long BLOCK_DURATION_MINUTES = 10;

    private final Map<String, AttemptInfo> attempts = new ConcurrentHashMap<>();

    public void loginFailed(String ip){
        AttemptInfo info = attempts.getOrDefault(ip, new AttemptInfo());
        info.increment();
        attempts.put(ip, info);
    }

    public void loginSucceeded(String ip){
        attempts.remove(ip);
    }

    public boolean isBlocked(String ip) {
        AttemptInfo info = attempts.get(ip);
        if (info == null){
            return false;
        }

        if(info.getAttempts() >= MAX_ATTEMPTS){
            if (info.getLastAttempt().plusMinutes(BLOCK_DURATION_MINUTES).isAfter(LocalDateTime.now())){
                return true;
            }else {
                attempts.remove(ip);
            }
        }
        return false;
    }

    private static class AttemptInfo{
        private int attempts = 0;
        private LocalDateTime lastAttempt = LocalDateTime.now();

        void increment(){
            attempts++;
            lastAttempt = LocalDateTime.now();
        }
        int getAttempts() {
            return attempts;
        }

        LocalDateTime getLastAttempt() {
            return lastAttempt;
        }
    }
}

package com.project.footfair.service;

import org.springframework.stereotype.Component;

@Component
public class LoginAttemptService {
    public boolean isBlocked(String ip) {
        return false;
    }
}

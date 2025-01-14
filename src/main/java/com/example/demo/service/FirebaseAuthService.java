package com.example.demo.service;

import com.example.demo.model.LoginRequest;
import com.example.demo.model.LoginResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.auth.UserRecord.UpdateRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class FirebaseAuthService {

    @Value("${firebase.api-key}")
    private String firebaseApiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public LoginResponse signup(LoginRequest signupRequest) {
        String url = "https://identitytoolkit.googleapis.com/v1/accounts:signUp?key=" + firebaseApiKey;

        Map<String, Object> request = new HashMap<>();
        request.put("email", signupRequest.getEmail());
        request.put("password", signupRequest.getPassword());
        request.put("returnSecureToken", true);

        Map<String, Object> response = restTemplate.postForObject(url, request, Map.class);
        String idToken = (String) response.get("idToken");

        return new LoginResponse(idToken);
    }

    public LoginResponse login(LoginRequest loginRequest) {
        String url = "https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key=" + firebaseApiKey;

        Map<String, Object> request = new HashMap<>();
        request.put("email", loginRequest.getEmail());
        request.put("password", loginRequest.getPassword());
        request.put("returnSecureToken", true);

        Map<String, Object> response = restTemplate.postForObject(url, request, Map.class);
        String idToken = (String) response.get("idToken");

        return new LoginResponse(idToken);
    }

    public UserRecord getUserByEmail(String email) throws FirebaseAuthException {
        return FirebaseAuth.getInstance().getUserByEmail(email);
    }

    public UserRecord updateUser(String uid, String email, String password)
            throws FirebaseAuthException {
        UpdateRequest request = new UpdateRequest(uid)
                .setEmail(email)
                .setPassword(password);

        return FirebaseAuth.getInstance().updateUser(request);
    }

    public void deleteUser(String uid) throws FirebaseAuthException {
        FirebaseAuth.getInstance().deleteUser(uid);
    }

    public FirebaseToken verifyIdToken(String idToken) throws FirebaseAuthException {
        return FirebaseAuth.getInstance().verifyIdToken(idToken);
    }
}
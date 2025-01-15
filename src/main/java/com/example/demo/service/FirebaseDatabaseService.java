package com.example.demo.service;

import com.example.demo.model.RegisterUserRequest;
import com.example.demo.model.User;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.concurrent.CompletableFuture;

@Service
public class FirebaseDatabaseService {

    private final FirebaseDatabase firebaseDatabase;

    @Autowired
    public FirebaseDatabaseService(FirebaseApp firebaseApp) {
        this.firebaseDatabase = FirebaseDatabase.getInstance(firebaseApp);
    }

    private DatabaseReference getDatabaseReference() {
        return firebaseDatabase.getReference("users");
    }

    public CompletableFuture<Void> createUser(User user) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                getDatabaseReference().child(String.valueOf(user.getId())).setValueAsync(user).get();
                return null;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    public DatabaseReference getUser(String id) {
        return getDatabaseReference().child(id);
    }

    public CompletableFuture<Void> updateUser(User user) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                getDatabaseReference().child(String.valueOf(user.getId())).setValueAsync(user).get();
                return null;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    public CompletableFuture<Void> deleteUser(String id) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                getDatabaseReference().child(id).removeValueAsync().get();
                return null;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}
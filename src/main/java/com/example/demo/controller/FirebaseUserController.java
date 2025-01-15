package com.example.demo.controller;

import com.example.demo.model.RegisterUserRequest;
import com.example.demo.model.User;
import com.example.demo.service.FirebaseDatabaseService;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/firebase/users")
public class FirebaseUserController {

    @Autowired
    private FirebaseDatabaseService firebaseDatabaseService;

    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestBody User user) {
        try {
            firebaseDatabaseService.createUser(user).get();
            return ResponseEntity.ok("User created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to create user: " + e.getMessage());
        }
    }

    @GetMapping("/get/{id}")
    public CompletableFuture<ResponseEntity<User>> getUser(@PathVariable String id) {
        CompletableFuture<ResponseEntity<User>> future = new CompletableFuture<>();
        DatabaseReference userRef = firebaseDatabaseService.getUser(id);

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    User user = dataSnapshot.getValue(User.class);
                    future.complete(ResponseEntity.ok(user));
                } else {
                    future.complete(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                future.completeExceptionally(databaseError.toException());
            }
        });

        return future;
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateUser(@RequestBody User user) {
        try {
            firebaseDatabaseService.updateUser(user).get();
            return ResponseEntity.ok("User updated successfully");
        } catch (InterruptedException | ExecutionException e) {
            return ResponseEntity.status(500).body("Failed to update user: " +
                    e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable String id) {
        try {
            firebaseDatabaseService.deleteUser(id).get();
            return ResponseEntity.ok("User deleted successfully");
        } catch (InterruptedException | ExecutionException e) {
            return ResponseEntity.status(500).body("Failed to delete user: " +
                    e.getMessage());
        }
    }
}
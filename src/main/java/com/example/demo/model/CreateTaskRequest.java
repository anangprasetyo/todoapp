package com.example.demo.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Builder

public class CreateTaskRequest {

    @NotBlank
    private int user_id;
    @NotBlank
    private String title;
    private String description;
    @NotBlank
    private String priority;
    private Boolean status;
}

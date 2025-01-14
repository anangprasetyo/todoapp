package com.example.demo.model;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Builder

public class UpdateTaskRequest {

    private String title;
    private String description;
    private String priority;
    private Boolean status;
}

package com.enrique.project1back.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    @Id
    private String id;
    private String userId;
    private String title;
    private String description;
    private boolean isDone;
}

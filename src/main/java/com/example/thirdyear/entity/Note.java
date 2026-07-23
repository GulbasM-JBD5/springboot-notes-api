package com.example.thirdyear.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


import java.time.LocalDate;


@Entity
@Table(name="notes")
public class Note {

    @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long  id ;
    @NotBlank(message="Title cannot be blank")
    @Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters")
    private String title ;
    @NotBlank(message="Content cannot be blank")
    @Size(min = 5, message = "Content must contain at least 5 characters")
    private String content;
    private LocalDate createdAt;
    public Note() {

    }
    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }
}

package com.example.thirdyear.entity;

import jakarta.persistence.*;

import java.time.LocalDate;


@Entity
@Table(name="notes")
public class Note {

    @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long  id ;
    private String title ;
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

package com.example.thirdyear.dto;

import com.example.thirdyear.entity.Note;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class NoteResponse{
        private Long  id ;
        private String title ;
        private String content;
        private LocalDate createdAt;
        public NoteResponse() {
        }
        public Long getId() {
            return id;}

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

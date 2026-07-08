package com.example.thirdyear.repository;

import com.example.thirdyear.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;
public interface NoteRepository extends JpaRepository <Note,Long>{
}

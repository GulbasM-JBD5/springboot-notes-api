
package com.example.thirdyear.repository;

import com.example.thirdyear.entity.Flashcard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface FlashcardRepository extends JpaRepository<Flashcard, Long> {

    List<Flashcard> findAllByUserIdOrderByCreatedAtDesc(Long userId);
}

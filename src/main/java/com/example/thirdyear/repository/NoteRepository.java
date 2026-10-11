package com.example.thirdyear.repository;

import com.example.thirdyear.entity.Note;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NoteRepository extends JpaRepository <Note,Long>{
     // List<Note> findByUserId(Long userId);
     Page<Note> findByUserId(Long userId, Pageable pageable);
     //search axtaris methodu  elave edilir
     /*Page<Note> findByUserIdAndTitleContainingIgnoreCaseOrUserIdAndContentContainingIgnoreCase(
             Long userId,
             String title,
             Long userId2,
             String content,
             Pageable pageable
     );
     */
     @Query("""
    SELECT n FROM Note n
    WHERE n.user.id = :userId
      AND (LOWER(n.title) LIKE LOWER(CONCAT('%', :search, '%'))
        OR LOWER(n.content) LIKE LOWER(CONCAT('%', :search, '%')))
""")
     Page<Note>  search(@Param("userId") Long userId,
                        @Param("search") String search,
                        Pageable pageable);
}

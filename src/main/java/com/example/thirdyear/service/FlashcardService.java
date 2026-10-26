
package com.example.thirdyear.service;

import com.example.thirdyear.dto.FlashcardRequest;
import com.example.thirdyear.dto.FlashcardResponse;
import com.example.thirdyear.entity.Flashcard;
import com.example.thirdyear.entity.User;
import com.example.thirdyear.repository.FlashcardRepository;
import com.example.thirdyear.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class FlashcardService {

    private final FlashcardRepository flashcardRepository;
    private final UserRepository userRepository;

    public FlashcardService(
            FlashcardRepository flashcardRepository,
            UserRepository userRepository) {
        this.flashcardRepository = flashcardRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public FlashcardResponse createFlashcard(FlashcardRequest request) {
        User user = getCurrentUser();

        Flashcard flashcard = new Flashcard();
        flashcard.setQuestion(request.getQuestion());
        flashcard.setAnswer(request.getAnswer());
        flashcard.setUser(user);

        Flashcard saved = flashcardRepository.save(flashcard);

        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<FlashcardResponse> getMyFlashcards() {
        User user = getCurrentUser();

        return flashcardRepository
                .findAllByUserIdOrderByCreatedAtDesc(user.getId())
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private User getCurrentUser() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null
                || !authentication.isAuthenticated()
                || "anonymousUser".equals(authentication.getPrincipal())) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "İstifadəçi autentifikasiya olunmayıb."
            );
        }

        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED,
                        "İstifadəçi tapılmadı."
                ));
    }

    private FlashcardResponse toResponse(Flashcard flashcard) {
        return new FlashcardResponse(
                flashcard.getId(),
                flashcard.getQuestion(),
                flashcard.getAnswer(),
                flashcard.getCreatedAt()
        );
    }
}

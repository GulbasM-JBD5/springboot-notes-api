
package com.example.thirdyear.controller;

import com.example.thirdyear.dto.FlashcardRequest;
import com.example.thirdyear.dto.FlashcardResponse;
import com.example.thirdyear.service.FlashcardService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/flashcards")
public class FlashcardController {

    private final FlashcardService flashcardService;

    public FlashcardController(FlashcardService flashcardService) {
        this.flashcardService = flashcardService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FlashcardResponse createFlashcard(
            @Valid @RequestBody FlashcardRequest request) {
        return flashcardService.createFlashcard(request);
    }

    @GetMapping
    public List<FlashcardResponse> getMyFlashcards() {
        return flashcardService.getMyFlashcards();
    }
}

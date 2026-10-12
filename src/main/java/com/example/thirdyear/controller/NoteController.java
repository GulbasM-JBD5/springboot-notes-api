package com.example.thirdyear.controller;

import com.example.thirdyear.dto.NotePageResponse;
import com.example.thirdyear.dto.NoteRequest;
import com.example.thirdyear.dto.NoteResponse;
import com.example.thirdyear.entity.Note;
import com.example.thirdyear.service.NoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notes")
public class NoteController {
    private final NoteService noteService;
    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }
    @PostMapping
     //  Note obyekyti deyil NoteRequest obyekti (RequestDto ya gore)
    public ResponseEntity<NoteResponse> addNote(@Valid@RequestBody NoteRequest noteRequest) {
        NoteResponse noteResponse = noteService.addNote(noteRequest);

        return ResponseEntity.status(201).body(noteResponse);
    }
    @GetMapping
    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    public NotePageResponse showAllNotes(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "15") int size,@RequestParam(defaultValue = "")
        String search) {
        return noteService.showAllNotes(page, size,search);
    }
    @Operation(security = @SecurityRequirement(name = "bearerAuth"))

    @GetMapping("/{id}")
    public NoteResponse showById(@PathVariable Long id){
         return noteService.showById(id);
    }
    @PutMapping("/{id}")
    public NoteResponse updateContent (@PathVariable Long id,@Valid @RequestBody NoteRequest noteRequest) {
        return noteService.updateContent(id, noteRequest);
    }
    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
@DeleteMapping("/{id}")
@ResponseStatus(HttpStatus.NO_CONTENT)
public ResponseEntity<Void> deleteNote(@PathVariable Long id){

        noteService.deleteNote(id);
    return ResponseEntity.noContent().build();
}
}

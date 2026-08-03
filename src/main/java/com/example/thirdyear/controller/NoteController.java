package com.example.thirdyear.controller;

import com.example.thirdyear.dto.NoteRequest;
import com.example.thirdyear.dto.NoteResponse;
import com.example.thirdyear.entity.Note;
import com.example.thirdyear.service.NoteService;
import jakarta.validation.Valid;
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
    public List<NoteResponse> showAllNotes() {
        return noteService.showAllNotes();
    }
    @GetMapping("/{id}")
    public NoteResponse showById(@PathVariable Long id){
         return noteService.showById(id);
    }
    @PutMapping("/{id}")
    public NoteResponse updateContent (@PathVariable Long id,@Valid @RequestBody NoteRequest noteRequest) {
        return noteService.updateContent(id, noteRequest);
    }

@DeleteMapping("/{id}")
public ResponseEntity<Void> deleteNote(@PathVariable Long id){

        noteService.deleteNote(id);
    return ResponseEntity.noContent().build();
}
}

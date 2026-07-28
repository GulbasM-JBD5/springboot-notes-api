package com.example.thirdyear.controller;

import com.example.thirdyear.dto.NoteRequest;
import com.example.thirdyear.dto.NoteResponse;
import com.example.thirdyear.entity.Note;
import com.example.thirdyear.service.NoteService;
import jakarta.validation.Valid;
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
    public NoteResponse addNote(@Valid@RequestBody NoteRequest noteRequest) {
        return noteService.addNote(noteRequest);
    }
    @GetMapping
    public List<NoteResponse> showAllNotes() {
        return noteService.showAllNotes();
    }
    @GetMapping("/{id}")
    public Note showById(@PathVariable Long id){
         return noteService.showById(id);
    }
    @PutMapping("/{id}")
    public Note updateContent (@PathVariable Long id,@RequestBody Note note) {
        return noteService.updateContent(id, note);
    }

@DeleteMapping("/{id}")
public void deleteNote(@PathVariable Long id){
        noteService.deleteNote(id);
}
}

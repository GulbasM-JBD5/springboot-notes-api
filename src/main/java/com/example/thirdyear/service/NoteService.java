package com.example.thirdyear.service;

import com.example.thirdyear.entity.Note;
import com.example.thirdyear.repository.NoteRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@Service
public class NoteService {
    private final NoteRepository noteRepository;
    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }
    public Note addNote(Note note) {
        System.out.println("Service method called");

        note.setCreatedAt(LocalDate.now());

        return noteRepository.save(note);
    }
    public List<Note> showAllNotes() {
        return noteRepository.findAll();
    }
    public Note showById (Long id){
       return  noteRepository.findById(id).orElseThrow(()->new ResponseStatusException(
               HttpStatus.NOT_FOUND,
               "Note tapılmadı."));
    }
    public Note updateContent(Long id,Note note){
        Note existingNote = noteRepository.findById(id).orElseThrow(()-> new ResponseStatusException(
                HttpStatus.NOT_FOUND,"Note taplmadı."));
           existingNote.setTitle(note.getTitle());
        existingNote.setContent(note.getContent()); ;
        return noteRepository.save(existingNote);
    }
    public void deleteNote(Long id){
        Note exsistingNote= noteRepository.findById(id).orElseThrow(()-> new ResponseStatusException(
                HttpStatus.NOT_FOUND,"Note taplmadı."));
        noteRepository.delete(exsistingNote);
    }


}

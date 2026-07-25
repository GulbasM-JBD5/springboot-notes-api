package com.example.thirdyear.service;

import com.example.thirdyear.dto.NoteResponse;
import com.example.thirdyear.entity.Note;
import com.example.thirdyear.repository.NoteRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
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
    public List<NoteResponse> showAllNotes() {
        //howAllNotes() metodunu DTO qaytaracaq vəziyyətə gətirməkdir
        List <NoteResponse> noteresponses=new ArrayList<>();
        List<Note> notes= noteRepository.findAll();
        for(Note nt:notes){
            NoteResponse noteresponse=new NoteResponse();
            noteresponse.setId(nt.getId());
            noteresponse.setContent(nt.getContent());
            noteresponse.setTitle(nt.getTitle());
            noteresponse.setCreatedAt(nt.getCreatedAt());
            noteresponses.add(noteresponse);

        }
        return noteresponses;
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

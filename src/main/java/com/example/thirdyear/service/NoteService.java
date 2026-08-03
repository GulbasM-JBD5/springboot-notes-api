package com.example.thirdyear.service;

import com.example.thirdyear.dto.NoteRequest;
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
    public NoteResponse addNote( NoteRequest noteRequest) {
        System.out.println("Service method called");
        Note note=new Note();
        note.setTitle(noteRequest.getTitle());
        note.setContent(noteRequest.getContent());
        note.setCreatedAt(LocalDate.now());
          Note savedNote= noteRepository.save(note);
          NoteResponse noteResponse=new NoteResponse();
        noteResponse.setTitle(savedNote.getTitle());
        noteResponse.setContent(savedNote.getContent());
        noteResponse.setCreatedAt(savedNote.getCreatedAt());
        noteResponse.setId(savedNote.getId());
        return noteResponse;
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
    public NoteResponse showById (Long id){


               Note note=noteRepository.findById(id).orElseThrow(()->new ResponseStatusException(
               HttpStatus.NOT_FOUND,
               "Note tapılmadı."));
       NoteResponse noteResponse=new NoteResponse();
       noteResponse.setTitle(note.getTitle());
        noteResponse.setContent(note.getContent());
        noteResponse.setCreatedAt(note.getCreatedAt());
        noteResponse.setId(note.getId());
       return noteResponse;

    }
    public NoteResponse updateContent(Long id,NoteRequest noteRequest){
        System.out.println("UPDATE SERVICE CALLED");
        //noterequest geldi title,content ,id var urlde ,noterepository isleyir ancaq note obyeki ile
        Note existingNote = noteRepository.findById(id).orElseThrow(()-> new ResponseStatusException(
                HttpStatus.NOT_FOUND,"Note taplmadı."));
           existingNote.setTitle(noteRequest.getTitle());
        existingNote.setContent(noteRequest.getContent()); ;
       noteRepository.save(existingNote);
       NoteResponse noteResponse=new NoteResponse();
       noteResponse.setTitle(existingNote.getTitle());
        noteResponse.setContent(existingNote.getContent());
        noteResponse.setId(existingNote.getId());
        noteResponse.setCreatedAt(existingNote.getCreatedAt());
        return noteResponse;
    }
    public void deleteNote(Long id){
        Note exsistingNote= noteRepository.findById(id).orElseThrow(()-> new ResponseStatusException(
                HttpStatus.NOT_FOUND,"Note taplmadı."));
        noteRepository.delete(exsistingNote);
    }


}

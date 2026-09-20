package com.example.thirdyear.Service;
import com.example.thirdyear.dto.NoteRequest;
import com.example.thirdyear.dto.NoteResponse;
import com.example.thirdyear.entity.Note;
import com.example.thirdyear.repository.NoteRepository;
import com.example.thirdyear.service.NoteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
 class NoteServiceTest {
    @Mock
    private NoteRepository noteRepository;

    @InjectMocks
    private NoteService noteService;
    @Test
    void addNote_shouldReturnNoteResponse() {
     NoteRequest noteRequest=new NoteRequest();
     noteRequest.setTitle("Test Title");
     noteRequest.setContent("Test Content");
     Note savedNote=new Note();
     savedNote.setId(1L);
     savedNote.setTitle("Test Title");
     savedNote.setContent("Test Content");
     savedNote.setCreatedAt(LocalDate.now());
        when(noteRepository.save(any(Note.class))).thenReturn(savedNote);
        NoteResponse response = noteService.addNote(noteRequest); //noteService real service classinin obyektidir,spring boot yox test muhiti yaradir
        assertEquals(1L, response.getId());
        assertEquals("Test Title", response.getTitle());
        assertEquals("Test Content", response.getContent());
        verify(noteRepository).save(any(Note.class));
    }
    //https://youtube.com/shorts/Cszh9XnH-tY?si=2xrXIc3GGOXCVknZ
    @Test
    void showAllNotes_shouldReturnNoteResponses(){
        Note note1 = new Note();
        note1.setId(1L);
        note1.setTitle("First Note");
        note1.setContent("First Content");
        note1.setCreatedAt(LocalDate.now());

        Note note2 = new Note();
        note2.setId(2L);
        note2.setTitle("Second Note");
        note2.setContent("Second Content");
        note2.setCreatedAt(LocalDate.now());
        List<Note> notes = List.of(note1, note2);
        when(noteRepository.findAll()).thenReturn(notes);
        List<NoteResponse> responses = noteService.showAllNotes();

        assertEquals(2, responses.size());

        assertEquals(1L, responses.get(0).getId());
        assertEquals("First Note", responses.get(0).getTitle());
        assertEquals("First Content", responses.get(0).getContent());

        assertEquals(2L, responses.get(1).getId());
        assertEquals("Second Note", responses.get(1).getTitle());
        assertEquals("Second Content", responses.get(1).getContent());

        verify(noteRepository).findAll();
    }
    // id ye gore tapmaq
    @Test
    void showById_shouldReturnNoteResponse(){
        Note note = new Note();
        note.setId(1L);
        note.setTitle("Test Note");
        note.setContent("Test Content");
        note.setCreatedAt(LocalDate.now());
        when(noteRepository.findById(1L))
                .thenReturn(Optional.of(note));
        NoteResponse response = noteService.showById(1L);
        assertEquals(1L, response.getId());
        assertEquals("Test Note", response.getTitle());
        assertEquals("Test Content", response.getContent());
        assertEquals(note.getCreatedAt(), response.getCreatedAt());
        verify(noteRepository).findById(1L);

    }
    //id tapilmassa
    @Test
    void showById_shouldThrowNotFound (){
        when(noteRepository.findById(999L))
                .thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> noteService.showById(999L)
        );

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());

        verify(noteRepository).findById(999L);
    }
    //update ugurlu

    @Test
    void updateContent_shouldReturnUpdatedNoteResponse() {

        NoteRequest noteRequest = new NoteRequest();
        noteRequest.setTitle("Updated Title");
        noteRequest.setContent("Updated Content");

        Note existingNote = new Note();
        existingNote.setId(1L);
        existingNote.setTitle("Old Title");
        existingNote.setContent("Old Content");
        existingNote.setCreatedAt(LocalDate.now());

        when(noteRepository.findById(1L))
                .thenReturn(Optional.of(existingNote));

        when(noteRepository.save(any(Note.class)))
                .thenReturn(existingNote);

        NoteResponse response =
                noteService.updateContent(1L, noteRequest);

        assertEquals(1L, response.getId());
        assertEquals("Updated Title", response.getTitle());
        assertEquals("Updated Content", response.getContent());
        assertEquals(existingNote.getCreatedAt(), response.getCreatedAt());

        verify(noteRepository).findById(1L);
        verify(noteRepository).save(existingNote);
    }
    //update ugursuz
    @Test
    void updateContent_shouldThrowNotFound() {

        NoteRequest noteRequest = new NoteRequest();
        noteRequest.setTitle("Updated Title");
        noteRequest.setContent("Updated Content");

        when(noteRepository.findById(999L))
                .thenReturn(Optional.empty());

        ResponseStatusException exception =
                assertThrows(
                        ResponseStatusException.class,
                        () -> noteService.updateContent(999L, noteRequest)
                );

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());

        verify(noteRepository).findById(999L);
        verify(noteRepository, never()).save(any(Note.class));
    }

    //delete silindi
    @Test
    void deleteNote_shouldDeleteNote() {

        Note existingNote = new Note();
        existingNote.setId(1L);
        existingNote.setTitle("Test Note");
        existingNote.setContent("Test Content");

        when(noteRepository.findById(1L))
                .thenReturn(Optional.of(existingNote));

        noteService.deleteNote(1L);

        verify(noteRepository).findById(1L);
        verify(noteRepository).delete(existingNote);
    }
    //delete ugursuz
    @Test
    void deleteNote_shouldThrowNotFound() {

        when(noteRepository.findById(999L))
                .thenReturn(Optional.empty());

        ResponseStatusException exception =
                assertThrows(
                        ResponseStatusException.class,
                        () -> noteService.deleteNote(999L)
                );

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());

        verify(noteRepository).findById(999L);
        verify(noteRepository, never()).delete(any(Note.class));
    }




}

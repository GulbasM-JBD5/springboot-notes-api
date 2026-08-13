package com.example.thirdyear.Controller;
import com.example.thirdyear.controller.NoteController;
import com.example.thirdyear.dto.NoteRequest;
import com.example.thirdyear.dto.NoteResponse;
import com.example.thirdyear.service.NoteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.http.MediaType;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

//Boş title və content gələndə @Valid request-i saxlasın və service-ə göndərməsin.
@WebMvcTest(NoteController.class)

class NoteControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private NoteService noteService;
    //400 bad requets qaytariri testi
    @Test
    void validationTest () throws Exception{
        String json = """
        {
            "title": "",
            "content": ""
        }
        """;
        mockMvc.perform(
                post("/notes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        )
                .andExpect(status().isBadRequest());
        //serviceye daxil olamdiq testi

        verify(noteService, never()).addNote(any(NoteRequest.class));
    }
    //Düzgün məlumat gələndə @Valid keçsin və service çağırılsın.
    @Test
    void addNote_withValidData() throws Exception {
        NoteResponse noteResponse=new NoteResponse();
        noteResponse.setId(1L);
        noteResponse.setTitle("My test");
                noteResponse.setContent("I couldn't understand this part");
        when(noteService.addNote(any(NoteRequest.class)))
                .thenReturn(noteResponse);
        String json = """
            {
                "title": "My test",
                "content": "I couldn't understand this part"
            }
            """;

        mockMvc.perform(
                post("/notes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("My test"))
                .andExpect(jsonPath("$.content").value("I couldn't understand this part"));
        verify(noteService).addNote(any(NoteRequest.class));
    }
    //get all notes test
    @Test
    void showAllNotes_shouldReturnNotes() throws Exception {
        NoteResponse note1 = new NoteResponse();
        note1.setId(1L);
        note1.setTitle("First Note");
        note1.setContent("First content");

        NoteResponse note2 = new NoteResponse();
        note2.setId(2L);
        note2.setTitle("Second Note");
        note2.setContent("Second content");

        List<NoteResponse> notes = List.of(note1, note2);
        when(noteService.showAllNotes()).thenReturn(notes);
        mockMvc.perform(
                get("/notes")
        ).andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].title").value("First Note"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].title").value("Second Note"));
        verify(noteService).showAllNotes();
    }
    //Showby id Test
    @Test
    void showById_shouldReturnNote() throws Exception {
        NoteResponse noteResponse = new NoteResponse();
        noteResponse.setId(1L);
        noteResponse.setTitle("First Note");
        noteResponse.setContent("First content");
        when(noteService.showById(1L)).thenReturn(noteResponse);
        mockMvc.perform(
                get("/notes/1")
        ).andExpect(status().isOk())



.andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("First Note"))
                .andExpect(jsonPath("$.content").value("First content"));
        verify(noteService).showById(1L);
    }
    // id tapilmassa test
    @Test
    void showById_shouldReturnNotFound() throws Exception {
        when(noteService.showById(999L))
                .thenThrow(new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Note tapılmadı."
                ));

        mockMvc.perform(
                        get("/notes/999")
                )
                .andExpect(status().isNotFound());

        verify(noteService).showById(999L);
    }
    //update test1
    @Test
    void updateNote_withValidData() throws Exception {
        NoteResponse noteResponse = new NoteResponse();
        noteResponse.setId(1L);
        noteResponse.setTitle("Updated Note");
        noteResponse.setContent("Updated content");
        when(noteService.updateContent(
                eq(1L),
                any(NoteRequest.class)
        )).thenReturn(noteResponse);
        String json = """
        {
            "title": "Updated Note",
            "content": "Updated content"
        }
        """;
        mockMvc.perform(
                        put("/notes/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Updated Note"))
                .andExpect(jsonPath("$.content").value("Updated content"));
        verify(noteService).updateContent(
                eq(1L),
                any(NoteRequest.class)
        );

    }
    //put  validasiya but
    @Test
    void updateNote_withInvalidData() throws Exception {

        String json = """
            {
                "title": "",
                "content": ""
            }
            """;

        mockMvc.perform(
                        put("/notes/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(status().isBadRequest());

        verify(noteService, never()).updateContent(
                eq(1L),
                any(NoteRequest.class)
        );
    }
    //put  id tapilmadi but
    @Test
    void updateNote_shouldReturnNotFound() throws Exception {

        when(noteService.updateContent(
                eq(999L),
                any(NoteRequest.class)
        )).thenThrow(new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Note tapılmadı."
        ));

        String json = """
            {
                "title": "Updated Note",
                "content": "Updated content"
            }
            """;

        mockMvc.perform(
                        put("/notes/999")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(status().isNotFound());

        verify(noteService).updateContent(
                eq(999L),
                any(NoteRequest.class)
        );
    }
    //delete
    @Test
    void deleteNote_shouldReturnNoContent() throws Exception {

        doNothing().when(noteService).deleteNote(1L);

        mockMvc.perform(
                        delete("/notes/1")
                )
                .andExpect(status().isNoContent());

        verify(noteService).deleteNote(1L);
    }
    //delete id tapilmadi
    @Test
    void deleteNote_shouldReturnNotFound() throws Exception {

        doThrow(new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Note tapılmadı."
        )).when(noteService).deleteNote(999L);

        mockMvc.perform(
                        delete("/notes/999")
                )
                .andExpect(status().isNotFound());

        verify(noteService).deleteNote(999L);
    }




}

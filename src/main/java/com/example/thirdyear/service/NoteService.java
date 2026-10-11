package com.example.thirdyear.service;
import com.example.thirdyear.dto.NotePageResponse;
import com.example.thirdyear.dto.NoteRequest;
import com.example.thirdyear.dto.NoteResponse;
import com.example.thirdyear.entity.Note;
import com.example.thirdyear.entity.User;
import com.example.thirdyear.repository.NoteRepository;
import com.example.thirdyear.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
@Service
public class NoteService {
    private final NoteRepository noteRepository;
    private final UserRepository userRepository;

    public NoteService(NoteRepository noteRepository,
     UserRepository userRepository) {
        this.noteRepository = noteRepository;
        this.userRepository = userRepository;
    }
    public NoteResponse  addNote(NoteRequest noteRequest) {
        System.out.println("Service method called");
        Note note=new Note();
        note.setTitle(noteRequest.getTitle());
        note.setContent(noteRequest.getContent());
        note.setCreatedAt(LocalDate.now());
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();
        System.out.println("AUTH EMAIL: " + email);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User tapılmadı."));
        System.out.println("USER FOUND: " + user.getEmail());
        note.setUser(user);
        System.out.println("ABOUT TO SAVE NOTE");
          Note savedNote= noteRepository.save(note);
        System.out.println("ABOUT TO SAVE NOTE");
          NoteResponse noteResponse=new NoteResponse();
        noteResponse.setTitle(savedNote.getTitle());
        noteResponse.setContent(savedNote.getContent());
        noteResponse.setCreatedAt(savedNote.getCreatedAt());
        noteResponse.setId(savedNote.getId());
        return noteResponse;
    }
    //pagination elave edirik
    //pagedto ile response deyisilir
    //search:axtaris methodu elave edirik
    public NotePageResponse showAllNotes(int page,int size,String search) {
        //howAllNotes() metodunu DTO qaytaracaq vəziyyətə gətirməkdir
        List <NoteResponse> noteresponses=new ArrayList<>();
         //List<Note> notes= noteRepository.findAll();
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        String email=authentication.getName();
        User user=userRepository.findByEmail(email).
                orElseThrow(() -> new RuntimeException("User tapılmadı."));
        //sorgu obyekti
        Pageable pageable = PageRequest.of(page,
                size,
                Sort.by( Sort.Order.desc("createdAt"),
                        Sort.Order.desc("id"))
                );
         //Page<Note> notes = noteRepository.findByUserId(user.getId(), pageable);
        Page<Note> notes;
        if (search == null || search.isBlank()) {
            notes = noteRepository.findByUserId(user.getId(), pageable);
        } else {
            notes = noteRepository.search(user.getId(), search.trim(), pageable);
        }
        for(Note nt:notes){
            NoteResponse noteresponse=new NoteResponse();
            noteresponse.setId(nt.getId());
            noteresponse.setContent(nt.getContent());
            noteresponse.setTitle(nt.getTitle());
            noteresponse.setCreatedAt(nt.getCreatedAt());
            noteresponses.add(noteresponse);

        }
        NotePageResponse pageResponse=new NotePageResponse();
        pageResponse.setNotes(noteresponses);
        pageResponse.setPageSize(notes.getSize());
        pageResponse.setTotalElements(notes.getTotalElements());
        pageResponse.setTotalPages(notes.getTotalPages());
        pageResponse.setCurrentPage(notes.getNumber());
        pageResponse.setHasNext(notes.hasNext());
        pageResponse.setHasPrevious(notes.hasPrevious());
        return pageResponse;
    }
    //jwt ile artiq method bir az deyisir
    public NoteResponse showById (Long id){


               Note note=noteRepository.findById(id).orElseThrow(()->new ResponseStatusException(
               HttpStatus.NOT_FOUND,
               "Note tapılmadı."));
               Authentication authentication=  SecurityContextHolder.getContext().getAuthentication();
               String email=authentication.getName();
        User user=userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User tapilmadi"));
        if (!note.getUser().getId().equals(user.getId())) {
            System.out.println("NOTE USER ID: " + note.getUser().getId());
            System.out.println("AUTH USER ID: " + user.getId());

            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Bu note-a giriş icazəniz yoxdur."
            );
        }
       NoteResponse noteResponse=new NoteResponse();
       noteResponse.setTitle(note.getTitle());
        noteResponse.setContent(note.getContent());
        noteResponse.setCreatedAt(note.getCreatedAt());
        noteResponse.setId(note.getId());
       return noteResponse;

    }
    //jwt ile update
    public NoteResponse updateContent(Long id,NoteRequest noteRequest){
        System.out.println("UPDATE SERVICE CALLED");
        //noterequest geldi title,content ,id var urlde ,noterepository isleyir ancaq note obyeki ile
        Note existingNote = noteRepository.findById(id).orElseThrow(()-> new ResponseStatusException(
                HttpStatus.NOT_FOUND,"Note taplmadı."));
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        String email=authentication.getName();
        System.out.println("AUTHENTICATION NAME: " + email);
        User user=userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User tapilmadi"));
        if (!existingNote.getUser().getId().equals(user.getId())) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Bu note-a giriş icazəniz yoxdur."
            );
        }
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
    //jwt ile delete
    public void deleteNote(Long id){
        Note exsistingNote= noteRepository.findById(id).orElseThrow(()-> new ResponseStatusException(
                HttpStatus.NOT_FOUND,"Note taplmadı."));
        Authentication authentication=  SecurityContextHolder.getContext().getAuthentication();
        String email= authentication.getName();
        User user=userRepository.findByEmail(email).
                orElseThrow(() -> new RuntimeException("User tapılmadı."));
        if (!exsistingNote.getUser().getId().equals(user.getId())) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Bu note-a giriş icazəniz yoxdur."
            );
        }
        noteRepository.delete(exsistingNote);
    }


}

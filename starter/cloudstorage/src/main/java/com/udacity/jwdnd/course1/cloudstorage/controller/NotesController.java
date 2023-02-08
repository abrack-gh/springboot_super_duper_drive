package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.boot.Banner;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class NotesController {
    private NoteService noteService;
    private UserMapper userMapper;
    private FileService fileService;
    private CredentialService credentialService;

    public NotesController(NoteService noteService, UserMapper userMapper, FileService fileService, CredentialService credentialService) {
        this.noteService = noteService;
        this.userMapper = userMapper;
        this.fileService = fileService;
        this.credentialService = credentialService;
    }

    @PostMapping("/notes")
    public String addNote(@ModelAttribute("credential")CredentialForm credentialForm, Model model, @ModelAttribute("note")NoteForm note,
                          Authentication authentication) {

        String username = authentication.getName();
        int userId = userMapper.getUser(username).getuserid();
        Notes notes = new Notes();

        notes.setUserId(userId);
        notes.setnoteTitle(notes.getnoteTitle());
        notes.setnoteDescription(notes.getnoteDescription());

        if (note.getnoteId() == null) {
            note.setnoteId(Integer.parseInt(note.getnoteId().toString()));
            noteService.updateNote(notes);
        } else {
            noteService.addNote(notes);
        }

        model.addAttribute("success", true);
        model.addAttribute("tab", "nav-notes-tab");
        model.addAttribute("notes", noteService.getNoteListings(userId));
        model.addAttribute("files", fileService.getUploadedFiles(userId));
        model.addAttribute("credentials", credentialService.getCredential(userId));

        return "home";
    }

    @DeleteMapping("/delete/{id}")
    private String deleteNote(@PathVariable(name = "id") String id, RedirectAttributes redirectAttributes) {
        noteService.deleteNote(Integer.valueOf(id));
        redirectAttributes.addFlashAttribute("tab", "nav-notes-tab");
        redirectAttributes.addFlashAttribute("success", true);

        return "redirect:/home";
    }

}

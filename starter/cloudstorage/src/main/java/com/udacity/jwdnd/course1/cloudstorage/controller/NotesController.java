package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.boot.Banner;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/note")
public class NotesController {
    private NoteService noteService;
    private UserService userService;

    public NotesController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    @PostMapping("/save")
    public String addNote(Authentication authentication, Notes notes, Model model){
        notes.setUserid(notes.getUserid());

        Boolean isSuccess = noteService.addNote(notes) > 0;
        return "redirect:/result?isSuccess=" + isSuccess;
    }

    @GetMapping("/delete")
    public String deleteNote(@RequestParam("id") int id){
        Boolean isSuccess = id > 0;

        if(isSuccess){
            noteService.deleteNote(id);
        }

        return "redirect:/result?isSuccess=" + isSuccess;
    }

}

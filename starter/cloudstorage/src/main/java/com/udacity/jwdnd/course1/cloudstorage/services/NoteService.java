package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NotesMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import org.springframework.stereotype.Service;

@Service

public class NoteService {
    private final UserMapper userMapper;
    private final NotesMapper notesMapper;

    public NoteService(NotesMapper notesMapper, UserMapper userMapper) {
        this.notesMapper = notesMapper;
        this.userMapper = userMapper;
    }

    public void addNote(String title, String description, String username){
        Integer userId = userMapper.getUser(username).getUserid();
        Notes note = new Notes(0, title, description, userId);
        notesMapper.insert(note);
    }
    public Notes[] getNoteListings(Integer userid){
        return notesMapper.getNotesForUser(userid);
    }
    public Notes getNote(Integer noteId) {
        return notesMapper.getNote(noteId);
    }

    public void deleteNote(Integer noteId) {
        notesMapper.deleteNote(noteId);
    }

    public void updateNote(Integer noteId, String title, String description) {
        notesMapper.updateNote(noteId, title, description);
    }

    public Object getUserNote(int userId) {
        return this.notesMapper.getNote(userId);
    }
}


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

    public int addNote(Notes notes){
        int id;
        if(notes.getNodeid() == null){
            id = notesMapper.insert(notes);
        } else {
            id = notesMapper.updateNote(notes);
        }

        return id;
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

    public Object getUserNote(int userId) {
        return this.notesMapper.getNote(userId);
    }
}


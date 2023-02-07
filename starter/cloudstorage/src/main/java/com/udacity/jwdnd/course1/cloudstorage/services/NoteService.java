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
        if(notes.getnoteId() == null){
            notesMapper.insert(notes);
        } else {
            notesMapper.updateNote(notes);
        }

        return 0;
    }
    public Notes[] getNoteListings(Integer userid){
        return notesMapper.getNotesForUser(userid);
    }
    public Notes getNote(Integer noteId) {
        return notesMapper.getNote(noteId);
    }

    public void updateNote(Notes notes){
        this.notesMapper.updateNote(notes);
    }

    public void deleteNote(Integer noteId) {
        notesMapper.deleteNote(noteId);
    }

    public Object getUserNote(int userid) {
        return this.notesMapper.getNote(userid);
    }
}


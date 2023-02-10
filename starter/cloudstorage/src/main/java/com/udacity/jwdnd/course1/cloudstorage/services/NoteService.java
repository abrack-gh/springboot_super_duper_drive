package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NotesMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class NoteService {
    private final UserMapper userMapper;
    private final NotesMapper notesMapper;

    public NoteService(NotesMapper notesMapper, UserMapper userMapper) {
        this.notesMapper = notesMapper;
        this.userMapper = userMapper;
    }

    public int addNote(Notes notes, String username){
        User user = this.userMapper.getUser(username);
        Integer userid = user.getuserid();
        String notetitle = notes.getNotetitle();
        String notedescription = notes.getNotedescription();
        return notesMapper.insert(notes);

    }
    public List<Notes> getNoteListings(Integer userid){
        return notesMapper.getAllNotes(userid);
    }
    public Notes getNote(String notetitle) {
        return notesMapper.getNote(notetitle);
    }

    public void updateNote(Notes notes, String username){
        User user = this.userMapper.getUser(username);
        Integer userid = user.getuserid();
        Integer noteId = notes.getNoteid();
        String notetitle = notes.getNotetitle();
        String notedescription = notes.getNotedescription();

        this.notesMapper.updateNote(noteId.intValue(), notetitle, notedescription, userid.intValue());

    }

    public void deleteNote(Integer noteId) {
        notesMapper.deleteNote(noteId);
    }

    public Object getAllNotes(int userid) {
        return this.notesMapper.getAllNotes(userid);
    }
}


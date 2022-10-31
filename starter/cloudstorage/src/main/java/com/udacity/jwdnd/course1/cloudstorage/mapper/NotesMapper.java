package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import org.apache.ibatis.annotations.*;

@Mapper
public interface NotesMapper {
    @Select("SELECT *  FROM NOTES WHERE userid = #{userid}")
    Notes[] getNotesForUser(Integer userId);

    @Select("SELECT * FROM NOTES")
    Notes[] getNoteListings();

    @Select("SELECT *  FROM NOTES WHERE noteid = #{noteId}")
    Notes getNotes(Integer notesId);
    @Select("SELECT * FROM NOTES WHERE noteid = #{noteId}")
    Notes getNote(Integer noteId);


    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid) VALUES (#{notetitle}, #{notedescription}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "noteid")
    int insert(Notes notes);

    @Delete("DELETE FROM NOTES WHERE noteid = #{noteid}")
    void deleteNote(Integer noteId);


    @Update("UPDATE NOTES SET notetitle = #{notetitle}, notdescription = #{notedescription} WHERE noteId = #{noteId}")
    void updateNote(Integer noteId, String title, String description);
}


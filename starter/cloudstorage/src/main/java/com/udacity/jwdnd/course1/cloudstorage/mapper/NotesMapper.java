package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import org.apache.ibatis.annotations.*;

@Mapper
public interface NotesMapper {
    @Select("SELECT *  FROM NOTES WHERE userId = #{userId}")
    Notes[] getNotesForUser(Integer userId);

    @Select("SELECT * FROM NOTES")
    Notes[] getNoteListings();

    @Select("SELECT *  FROM NOTES WHERE noteId = #{noteId}")
    Notes getNotes(Integer notesId);
    @Select("SELECT * FROM NOTES WHERE noteId = #{noteId}")
    Notes getNote(Integer noteId);


    @Insert("INSERT INTO NOTES (noteTitle = #{noteTitle}, noteDescription =  #{noteDescription}, userId = #{userId}")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    int insert(Notes notes);

    @Delete("DELETE FROM NOTES WHERE noteId = #{noteId}")
    void deleteNote(Integer noteId);


    @Update("UPDATE NOTES " +
            " SET noteTitle = #{noteTitle}, noteDescription = #{noteDescription} " +
            " WHERE noteId = #{noteId}")
    int updateNote(Notes notes);

}


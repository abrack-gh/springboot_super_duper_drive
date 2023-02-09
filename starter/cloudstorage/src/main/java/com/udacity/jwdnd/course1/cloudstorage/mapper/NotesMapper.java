package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NotesMapper {
    @Select("SELECT *  FROM NOTES WHERE notetitle = #{notetitle}")
    Notes getNote(String noteTitle);

    @Select("SELECT * FROM NOTES WHERE userid=#{notetitle}")
    List<Notes> getAllNotes(Integer userid);

    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid)" + "VALUES(#{notetitle}, #{notedescription}, #{userid}")
    @Options(useGeneratedKeys = true, keyProperty = "noteid")
    void insert(Notes notes);

    @Delete("DELETE FROM NOTES WHERE noteId = #{noteid}")
    void deleteNote(Integer noteId);

    @Update("UPDATE NOTES " +
            " SET notetitle = #{notetitle}, notedescription = #{notedescription} " +
            " WHERE noteid = #{noteid}")
    int updateNote(Notes notes);

}


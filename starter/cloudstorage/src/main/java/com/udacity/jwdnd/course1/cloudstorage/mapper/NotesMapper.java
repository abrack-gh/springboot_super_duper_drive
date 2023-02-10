package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NotesMapper {
    @Select("SELECT *  FROM NOTES WHERE notetitle = #{notetitle}")
    Notes getNote(String notetitle);

    @Select("SELECT * FROM NOTES WHERE userid=#{notetitle}")
    List<Notes> getAllNotes(Integer userid);

    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid)" + "VALUES(#{notetitle}, #{notedescription}, #{userid}")
    @Options(useGeneratedKeys = true, keyProperty = "noteid")
    int insert(Notes notes);

    @Delete("DELETE FROM NOTES WHERE noteId = #{noteid}")
    void deleteNote(Integer noteId);

    @Update("UPDATE NOTES SET notetitle = #{notetitle}, notedescription = #{notedescription} , userid = #{userid} WHERE noteid = #{noteid}")
    void updateNote(Integer noteid, String notetitle, String notedescription, Integer userid);

}


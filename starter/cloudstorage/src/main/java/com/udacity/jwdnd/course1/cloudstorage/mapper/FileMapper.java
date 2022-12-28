package com.udacity.jwdnd.course1.cloudstorage.mapper;

import org.apache.ibatis.annotations.*;

import com.udacity.jwdnd.course1.cloudstorage.model.File;

import java.util.List;
import java.util.Map;

@Mapper
public interface FileMapper {

    @Select("SELECT * FROM FILES WHERE fileId = #{fileId}")
    File getFileById(Integer fileId);

    @Insert("INSERT INTO FILES(" +
            "filename, " +
            "fileid," +
            "contenttype, " +
            "filesize, " +
            "filedata, " +
            "userid) VALUES (" +
            "#{filename}, " +
            "#{fileId}, " +
            "#{contenttype}, " +
            "#{filesize}, " +
            "#{filedata}, " +
            "#{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int insert(File file);

    @Select("SELECT * FROM FILES")
    List<File> getAllFiles();

    @Delete("DELETE FROM FILES WHERE fileid = #{fileid}")
    void delete(Integer fileid);

    @Select("SELECT * FROM FILES WHERE userid = #{userid} AND filename = #{filename}")
    List<File> getFileByUsernameAndFileName(Map<String, Object> paraMap);

}


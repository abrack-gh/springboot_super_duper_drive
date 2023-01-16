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
            "fileName, " +
            "fileId," +
            "contentType, " +
            "fileSize, " +
            "fileData, " +
            "userId) VALUES (" +
            "#{fileName}, " +
            "#{fileId}, " +
            "#{contentType}, " +
            "#{fileSize}, " +
            "#{fileData}, " +
            "#{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int insert(File file);

    @Select("SELECT * FROM FILES")
    List<File> getAllFiles();

    @Delete("DELETE FROM FILES WHERE fileId = #{fileId}")
    void delete(Integer fileId);

    @Select("SELECT * FROM FILES WHERE userId = #{userId} AND fileName = #{fileName}")
    List<File> getFileByUsernameAndfileName(Map<String, Object> paraMap);

}


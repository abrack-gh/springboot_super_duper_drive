package com.udacity.jwdnd.course1.cloudstorage.mapper;

import org.apache.ibatis.annotations.*;

import com.udacity.jwdnd.course1.cloudstorage.model.File;

import java.util.List;
import java.util.Map;

@Mapper
public interface FileMapper {

    @Select("SELECT * FROM FILES WHERE fileId = #{fileId}")
    File getFileById(Integer fileId);

    @Insert("INSERT INTO FILES (fileName, contentType, fileSize, fileData, userId)"
            + "VALUES(#{fileName}, #{fileName}, #{contentType},#{fileSize}, #{fileData}, #{userId}")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int insert(File file);

    @Select("SELECT * FROM FILES")
    List<File> getAllFiles();

    @Select("SELECT * FROM FILES WHERE filename = #{fileName}")
    File getFile(String fileName);

    @Delete("DELETE FROM FILES WHERE fileId = #{fileId}")
    void delete(Integer fileId);

    @Select("SELECT * FROM FILES WHERE userid = #{userid} AND fileName = #{fileName}")
    List<File> getFileByUsernameAndfileName(Map<String, Object> paraMap);

}


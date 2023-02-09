package com.udacity.jwdnd.course1.cloudstorage.mapper;

import org.apache.ibatis.annotations.*;

import com.udacity.jwdnd.course1.cloudstorage.model.File;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Mapper
public interface FileMapper {

    @Select("SELECT * FROM FILES WHERE fileId = #{fileId}")
    File getFile(Integer fileId);

    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) VALUES (#{filename}, " +
            "#{contenttype}, #{filesize}, #{userid}, #{filedata} )")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    Integer insertFile(File file);

    @Select("SELECT * FROM FILES")
    List<File> getAllFiles(Integer userid);

    @Delete("DELETE FROM FILES WHERE fileId = #{fileId}")
    boolean delete(Integer fileId);

    @Update("UPDATE FILES SET filename = #{filename}, contenttype = #{contenttype}, filesize = #{filesize},filedata = #{filedata} " +
            "WHERE fileId = #{fileId}")
    void updateUserFile(Integer fileId, String filename, String contenttype, String filesize, InputStream filedata, Integer userid);

    @Select("SELECT * FROM FILES WHERE userid = #{userid} AND fileName = #{fileName}")
    List<File> getFileByUsernameAndfileName(Map<String, Object> paraMap);



}


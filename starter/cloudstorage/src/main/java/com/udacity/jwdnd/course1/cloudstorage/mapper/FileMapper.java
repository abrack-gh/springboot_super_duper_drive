package com.udacity.jwdnd.course1.cloudstorage.mapper;

import org.apache.ibatis.annotations.*;

import com.udacity.jwdnd.course1.cloudstorage.model.File;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Mapper
public interface FileMapper {

    @Select("SELECT * FROM FILES WHERE fileid = #{fileid}")
    File getFile(Integer fileid);

    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) VALUES (#{filename}, " +
            "#{contenttype}, #{filesize}, #{userid}, #{filedata} )")
    @Options(useGeneratedKeys = true, keyProperty = "fileid")
    int insertFile(File file);

    @Select("SELECT * FROM FILES")
    List<File> getAllFiles(Integer userid);

    @Delete("DELETE FROM FILES WHERE fileid = #{fileid}")
    boolean delete(Integer fileid);

    @Update("UPDATE FILES SET filename = #{filename}, contenttype = #{contenttype}, filesize = #{filesize},filedata = #{filedata} " +
            "WHERE fileid = #{fileid}")
    void updateUserFile(Integer fileid, String filename, String contenttype, String filesize, InputStream filedata, Integer userid);

    @Select("SELECT * FROM FILES WHERE userid = #{userid} AND filename = #{filename}")
    List<File> getFileByUsernameAndfilename(Map<String, Object> paraMap);



}


package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {

    @Insert("INSERT INTO CREDENTIALS (credentialId, url, username, key, password, userId)" + "VALUES (#{credentialId}, #{url}, #{username}, #{key}, #{password}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    int insert(Credential credential);

    @Select("SELECT * FROM CREDENTIALS WHERE credentialId = #{credentialId}")
    Credential getCredentialBycredentialId(Integer credentialId);

    @Select("SELECT * FROM CREDENTIALS WHERE username = #{username}")
    Credential getCredentialByCredentialByUsername(String username);

    @Select("SELECT * FROM CREDENTIALS WHERE username = #{userId}")
    List<Credential> getAllCredentials(int userId);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialId = #{credentialId}")
    Boolean delete(Integer credentialId);

    @Update("UPDATE credentials " +
            "SET url = #{url}, username = #{username}, key = #{key}, " +
            "password = #{password} " +
            "WHERE credentialId = #{credentialId}")
    int update(
            String url, String username, String key, String password, Integer credentialId);
}
package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {

    @Insert("INSERT INTO CREDENTIALS (url, username, key, password, userid) " +
            "VALUES(#{url}, #{username}, #{key}, #{password}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    void insert(Credential credential);

    @Select("SELECT * FROM CREDENTIALS WHERE credentialId = #{credentialId}")
    Credential getCredentialBycredentialId(Integer credentialId);

    @Select("SELECT * FROM CREDENTIALS WHERE username = #{username}")
    Credential getCredentialByCredentialByUsername(String username);

    @Select("SELECT * FROM CREDENTIALS WHERE userId = #{userid}")
    List<Credential> getAllCredentials(int userid);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialId = #{credentialId}")
    Boolean delete(Integer credentialId);

    @Update("UPDATE CREDENTIALS SET url = #{url}, key = #{key}, password = #{password}, username = #{newUserName} WHERE credentialId = #{credentialId}")
    void update(Integer credentialId, String newUserName, String url, String key, String password);
}
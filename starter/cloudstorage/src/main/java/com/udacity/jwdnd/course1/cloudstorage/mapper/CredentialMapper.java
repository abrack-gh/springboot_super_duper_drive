package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.*;

@Mapper
public interface CredentialMapper {

    @Insert("INSERT INTO CREDENTIALS(url, username, key, password, userid) VALUES (" +
            "#{url}, #{username}, #{key}, #{password}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialid")
    int insert(String url, String username, String key, String password, Integer credentialid);

    @Select("SELECT * FROM CREDENTIALS WHERE credentialid = #{credentialid}")
    Credential getCredentialByCredentialId(Integer credentialid);

    @Select("SELECT * FROM CREDENTIALS WHERE username = #{username}")
    Credential getCredentialByCredentialByUsername(String username);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialid = #{credentialid}")
    Boolean delete(Integer credentialid);

    @Update("UPDATE credentials " +
            "SET url = #{url}, username = #{username}, key = #{key}, " +
            "password = #{password} " +
            "WHERE credentialid = #{credentialid}")
    int update(
            String url, String username, String key, String password, Integer credentialid);
}
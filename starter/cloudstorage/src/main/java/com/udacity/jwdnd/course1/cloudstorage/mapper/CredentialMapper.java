package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {

    @Select("SELECT * FROM CREDENTIALS WHERE userId = #{userid}")
    List<Credential> getAllCredentials(int userid);

    @Select("SELECT * FROM CREDENTIALS WHERE url = #{url}")
    Credential getCredential(String url);

    @Insert("INSERT INTO CREDENTIALS (url, username, key, password, userid) VALUES (#{url}, " +
            "#{username}, #{key}, #{password}, #{userid} )")
    @Options(useGeneratedKeys = true, keyProperty = "credentialid")
    Integer insertCredential(Credential credential);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialId = #{credentialId}")
    Boolean delete(Integer credentialId);

    @Update("UPDATE CREDENTIALS SET url = #{url}, key = #{key}, password = #{password}, username = #{newUserName} WHERE credentialId = #{credentialId}")
    void update(Integer credentialId, String newUserName, String url, String key, String password, Integer userid);
}
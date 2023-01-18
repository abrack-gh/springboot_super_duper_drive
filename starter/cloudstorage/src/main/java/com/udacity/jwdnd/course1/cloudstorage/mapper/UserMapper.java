package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM USERS WHERE username = #{username}")
    User getUser(String username);

    @Select("SELECT * FROM USERS WHERE userId = #{userId}")
    User getUserById(Integer userId);

    @Select("SELECT * FROM USERS WHERE username = #{username}")
    User getUserByUsername(String username);

    @Select("SELECT * FROM USERS")
    List<User> getAllUsers();

    @Insert("INSERT INTO USERS (username, salt, password, firstName, lastName)" + "VALUES (#{username}, #{salt}, #{password}, #{firstName}, #{lastName})")
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    int insert(User user);

    @Delete("DELETE FROM USERS WHERE userId = #{userId}")
    void delete(Integer userId);
}
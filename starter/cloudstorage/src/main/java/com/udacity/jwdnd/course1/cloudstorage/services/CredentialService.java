package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.springframework.stereotype.Service;

@Service
public class CredentialService {
    private final UserMapper userMapper;
    private final CredentialMapper credentialMapper;

    public CredentialService(UserMapper userMapper, CredentialMapper credentialMapper) {
        this.userMapper = userMapper;
        this.credentialMapper = credentialMapper;
    }

    public UserMapper getUserMapper() {
        return userMapper;
    }

    public CredentialMapper getCredentialMapper() {
        return credentialMapper;
    }

    public void addCredential(String url, String username, String credentialUsername, String password, Integer key){
        Integer userid = userMapper.getUser(username).getUserid();
        Credential credential = new Credential(0, url, credentialUsername, key, password, userid);
        credentialMapper.insert(credential);}

    public Credential[] getCredentialListings(Integer userid) {
        return credentialMapper.getCredentialListings(userid);
    }

    public Credential getCredential(Integer noteid){
        return credentialMapper.getCredential(noteid);
    }

    public void deleteCredential(Integer noteId) {
        credentialMapper.deleteCredential(noteId);
    }
    public void updateCredential(Integer credentialid, String newUsername, String url, String key, Integer password){
        credentialMapper.updateCredential(credentialid, newUsername, url, key, password);
    }
}

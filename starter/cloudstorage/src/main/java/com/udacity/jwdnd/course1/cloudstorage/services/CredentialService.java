package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialStore;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CredentialService {

    private CredentialMapper credentialMapper;
    private EncryptionService encryptionService;
    private UserMapper userMapper;


    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService, UserMapper userMapper) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
        this.userMapper = userMapper;
    }

    public void addCredential(String url, String username, String credentialUserName, String key, String password) {
        Integer userid = userMapper.getUser(username).getuserid();
        Credential credential = new Credential(0, url, credentialUserName, key, password, userid);
        credentialMapper.insert(credential);
    }

    public Credential getCredential(int credentialId){
        return credentialMapper.getCredentialBycredentialId(credentialId);
    }

    public List<Credential> getAllCredentials(int credentialId) {

        List<Credential> credentialList = credentialMapper.getAllCredentials(credentialId);

        for (Credential credential : credentialList) {
            credential.setUnencodedPassword(encryptionService.decryptValue(credential.getPassword(), credential.getKey()));
        }

        return credentialList;
    }

        public Boolean deleteCredentials (Integer credentialId){
            return credentialMapper.delete(credentialId);
        }

        public void update(Integer credentialId, String newUsername, String newUrl, String key, String newPassword){
        credentialMapper.update(credentialId, newUsername, newUrl, key, newPassword);
        }
}





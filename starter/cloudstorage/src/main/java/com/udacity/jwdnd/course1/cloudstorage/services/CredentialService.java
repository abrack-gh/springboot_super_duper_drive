package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CredentialService {

    private CredentialMapper credentialMapper;
    private EncryptionService encryptionService;

    private UserService userService;
    private UserMapper userMapper;


    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService, UserMapper userMapper, UserService userService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
        this.userMapper = userMapper;
        this.userService = userService;
    }

    public int addCredential(Credential credential, String username) {
        User user = userService.getUser(username);
        Integer userId = userService.getUser(username).getuserid();
        SecureRandom secureRandom = new SecureRandom();
        byte[] key = new byte[16];
        secureRandom.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), encodedKey);

        return credentialMapper.insertCredential(new Credential(null, credential.getUrl(), credential.getUrl(), credential.getUsername(), encodedKey, encryptedPassword, userId.intValue()));
    }

    public List<Credential> getAllCredentials(int userId) {

        return credentialMapper.getAllCredentials(userId);
    }

    public Boolean deleteCredentials(Integer credentialId) {
        return credentialMapper.delete(credentialId);
    }

    public void updateCredential(Credential credential, String username) {
        Integer credentialId = credential.getCredentialId();
        String url = credential.getUrl();
        String credentialUsername = credential.getUsername();
        SecureRandom secureRandom = new SecureRandom();
        byte[] key = new byte[16];
        secureRandom.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = this.encryptionService.encryptValue(credential.getPassword(), encodedKey);
        User user = this.userService.getUser(username);
        Integer userid = userService.getUser(username).getuserid();

        this.credentialMapper.update(credentialId, url, credentialUsername, encodedKey, encryptedPassword, userid);
    }

    public Map<Integer, String> getUnencryptedPassword(Integer userId){
        Map<Integer, String> passwordMap = new HashMap<>();
        List<Credential> credentialList = credentialMapper.getAllCredentials(userId);
        for(Credential credential : credentialList){
            passwordMap.put(credential.getCredentialId(), encryptionService.decryptValue(credential.getPassword(), credential.getKey()));
        }
        return passwordMap;
    }
}





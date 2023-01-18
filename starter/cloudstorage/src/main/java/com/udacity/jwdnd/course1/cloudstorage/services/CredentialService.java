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

    public void addCredential(String url, String userName, String credentialUserName, String key, String password) {
        Integer userId = userMapper.getUser(userName).getuserId();
        Credential credential = new Credential(0, url, credentialUserName, key, password, userId);
        credentialMapper.insert(credential);
    }

    public Credential getCredential(int credentialId){
        return credentialMapper.getCredentialBycredentialId(credentialId);
    }

    public List<Credential> getAllCredentials(int credentialId) {

        List<Credential> credentialList = credentialMapper.getAllCredentials(credentialId);

        for(Credential credential : credentialList){
            credential.setUnencodedPassword(encryptionService.decryptValue(credential.getPassword(), credential.getKey()));
        }

        return credentialList;
    }

    public Boolean insertOrUpdateCredential(
            CredentialStore credentialStore, String username) {

        String password = credentialStore.getPassword();

        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(password, encodedKey);

        Integer credentialId = credentialStore.getcredentialId();

        if (credentialId != null) {
            this.credentialMapper.update(
                    credentialStore.getUrl(),
                    credentialStore.getUsername(),
                    encodedKey,
                    encryptedPassword,
                    credentialId);
        }

        return true;
    }

        public Boolean deleteCredentials (Integer credentialId){
            return credentialMapper.delete(credentialId);
        }

}





package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CredentialService {

    private CredentialMapper credentialMapper;
    private EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    public List<Credential> getCredentials(int userid){
        return credentialMapper.getCredentials(userid);
    }

    public void addCredentials(Credential credential, int userId){
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), encodedKey);

        Credential newCredential = new Credential();
        newCredential.setUrl(credential.getUrl());
        newCredential.setUserName(credential.getUserName());
        newCredential.setKey(encodedKey);
        newCredential.setPassword(encryptedPassword);
        newCredential.setUserid(userId);


        credentialMapper.insertCredentials(newCredential);
    }

    public int deleteCredentials(int credentialid){
        return credentialMapper.deleteCredentials(credentialid);
    }

    public void editCredentials(Credential credential){
        Credential storedCredential = credentialMapper.getCredentialById(credential.getCredentialid());

        credential.setKey(storedCredential.getKey());
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), credential.getKey());
        credential.setPassword(encryptedPassword);
        credentialMapper.updateCredentials(credential);
    }

    public Object getUserCredentials(int userId) {
        List<Credential> credentialList = this.credentialMapper.getCredentials(userId);
        return credentialList.stream().map(credential -> wrapCredential(credential)).collect(Collectors.toList());
    }

    private Credential wrapCredential(Credential c) {
        Credential mapped =  new Credential(c.getCredentialid(), c.getUrl(), c.getUserName(),
                null, c.getPassword(), c.getUserid());
        mapped.setUnencodedPassword(getUnencodedPassword(c));
        return mapped;
    }

    private String getUnencodedPassword(Credential c) {
        return this.encryptionService.decryptValue(c.getPassword(), wrapCredential(c).getKey());
    }
}




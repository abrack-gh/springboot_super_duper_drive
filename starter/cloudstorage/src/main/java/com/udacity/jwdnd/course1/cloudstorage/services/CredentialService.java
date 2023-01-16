package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
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

    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
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

        Integer credentialId = credentialStore.getCredentialId();

        if (credentialId == null) {

            this.credentialMapper.insert(
                    credentialStore.getUrl(),
                    credentialStore.getUsername(),
                    encodedKey,
                    encryptedPassword,
                    Integer.valueOf(username));
        } else {

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





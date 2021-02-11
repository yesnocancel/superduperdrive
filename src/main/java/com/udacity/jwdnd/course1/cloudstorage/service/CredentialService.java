package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialAndDecrypted;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CredentialService {
    private CredentialMapper credentialMapper;
    private EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    public List<Credential> getCredentialListForUser(Integer userId) {
        return credentialMapper.getCredentialsForUser(userId);
   }

    public List<CredentialAndDecrypted> getDecryptedPasswords(List<Credential> credentialList) {
        List<CredentialAndDecrypted> resultList = new ArrayList<CredentialAndDecrypted>();

        for (Credential cre : credentialList) {
            String decryptedPassword = encryptionService.decryptValue(cre.getPassword(), cre.getKey());
            CredentialAndDecrypted creAndDec = new CredentialAndDecrypted(cre, decryptedPassword);
            resultList.add(creAndDec);
        }

        return resultList;
    }

    public void saveCredential(Integer credentialid, String url, String username, String password, Integer userId) {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(password, encodedKey);

        Credential credential = new Credential(credentialid, url, username, encodedKey, encryptedPassword, userId);

        if (credentialid == null) {
            credentialMapper.insert(credential);
        } else {
            credentialMapper.update(credential);
        }
    }

    public void deleteCredential(Integer credentialid, Integer userId) {
        // Make sure that we delete a credential only if the user id matches!
        Credential credential = credentialMapper.getCredentialByCredentialId(credentialid);

        if (credential != null && credential.getUserid() == userId) {
            credentialMapper.delete(credentialid);
        }
    }
}

package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CredentialService {
    private CredentialMapper credentialMapper;
    private HashService hashService;

    public CredentialService(CredentialMapper credentialMapper, HashService hashService) {
        this.credentialMapper = credentialMapper;
        this.hashService = hashService;
    }

    public List<Credential> getCredentialListForUser(Integer userId) {
        return credentialMapper.getCredentialsForUser(userId);
    }

    public void saveCredential(Integer credentialid, String url, String username, String password, Integer userId) {
        String key = hashService.getHashedValue(password, "salt");
        Credential credential = new Credential(credentialid, url, username, key, password, userId);

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

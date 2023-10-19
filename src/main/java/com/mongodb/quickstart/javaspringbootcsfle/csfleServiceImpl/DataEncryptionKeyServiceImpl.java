package com.mongodb.quickstart.javaspringbootcsfle.csfleServiceImpl;

import com.mongodb.client.model.vault.DataKeyOptions;
import com.mongodb.client.vault.ClientEncryption;
import com.mongodb.quickstart.javaspringbootcsfle.csfleService.DataEncryptionKeyService;
import org.bson.BsonBinary;
import org.bson.BsonDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import static java.util.List.of;

@Service
public class DataEncryptionKeyServiceImpl implements DataEncryptionKeyService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataEncryptionKeyServiceImpl.class);
    private final ClientEncryption clientEncryption;
    @Value("${mongodb.kms.provider}")
    private String KMS_PROVIDER;
    private Map<String, String> dataEncryptionKeysB64 = new HashMap<>();

    public DataEncryptionKeyServiceImpl(ClientEncryption clientEncryption) {
        this.clientEncryption = clientEncryption;
    }

    public Map<String, String> getDataEncryptionKeysB64() {
        LOGGER.info("=> Getting Data Encryption Keys Base64 Map.");
        LOGGER.info("=> Size map: " + dataEncryptionKeysB64.size());
        return dataEncryptionKeysB64;
    }

    public String createOrRetrieveDEK(String dekName) {
        Base64.Encoder b64Encoder = Base64.getEncoder();
        BsonDocument dek = clientEncryption.getKeyByAltName(dekName);
        BsonBinary dataKeyId;
        if (dek == null) {
            LOGGER.info("=> Creating Data Encryption Key: " + dekName);
            DataKeyOptions dko = new DataKeyOptions().keyAltNames(of(dekName));
            dataKeyId = clientEncryption.createDataKey(KMS_PROVIDER, dko);
            LOGGER.debug("=> Person Collection DEK ID: " + dataKeyId);
        } else {
            LOGGER.info("=> Existing Data Encryption Key: " + dekName);
            dataKeyId = dek.get("_id").asBinary();
            LOGGER.debug("=> Person Collection DEK ID: " + dataKeyId);
        }
        String dek64 = b64Encoder.encodeToString(dataKeyId.getData());
        LOGGER.debug("=> Person Collection Base64 DEK ID: " + dek64);
        dataEncryptionKeysB64.put(dekName, dek64);
        return dek64;
    }

}

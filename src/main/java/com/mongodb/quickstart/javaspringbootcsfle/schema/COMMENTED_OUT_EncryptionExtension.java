/*
package com.mongodb.quickstart.javaspringbootcsfle.ccschema;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.spel.spi.EvaluationContextExtension;
import org.springframework.data.spel.spi.Function;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;

@Component
public class EncryptionExtension implements EvaluationContextExtension {

    private static final Logger LOGGER = LoggerFactory.getLogger(EncryptionExtension.class);

    @Override
    public String getExtensionId() {
        LOGGER.info("!!!=> getExtensionId");
        return "mongocrypt";
    }

    @Override
    public Map<String, Function> getFunctions() {
        LOGGER.info("!!!=> getFunctions");
        try {
            return Collections.singletonMap("keyId", new Function(EncryptionExtension.class.getMethod("computeKeyId", String.class), this));
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public String computeKeyId(String target) {
        LOGGER.info("!!!=> Computing keyId for target: " + target);
        return "AMDd3VC/RV6efGNXD4dwbw=="; // todo actually make this dynamic
    }
}*/

package com.mongodb.quickstart.javaspringbootcsfle.dto;

import com.mongodb.quickstart.javaspringbootcsfle.model.CompanyEntity;
import org.bson.types.ObjectId;

/**
 * Company DTO used for all the communication with the REST API.
 * Doesn't have to map 1:1 with the entity, but it does for this simple example.
 *
 * @param id
 * @param name
 * @param money
 */
public record CompanyDTO(String id, String name, Long money) {

    public CompanyDTO(CompanyEntity c) {
        this(c.getId().toHexString(), c.getName(), c.getMoney());
    }

    public CompanyEntity toCompanyEntity() {
        ObjectId _id = id == null ? new ObjectId() : new ObjectId(id);
        return new CompanyEntity(_id, name, money);
    }

}

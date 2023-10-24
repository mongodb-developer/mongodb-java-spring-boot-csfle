# Java Spring Boot Template with MongoDB CSFLE.

This project is a template for a Java Spring Boot application with MongoDB Client-Side Field Level Encryption using
Spring Data MongoDB.

For more information about this repository, read the associated [blog post](TODO: add link)

The goal was to provide reusable classes and methods to easily implement MongoDB CSFLE in an existing Java Spring Boot
application.

Also, most tutorials I see online only work with a single encrypted collection and don't really plan for more than one
encrypted collection.

# Prerequisites

- Java 17 (can't use Java 21 yet because Spring Boot 3.1.4 is not compatible)
- [MongoDB Cluster](https://www.mongodb.com/atlas/database) v7.0.2 or higher.
- [MongoDB Automatic Encryption Shared Library](https://www.mongodb.com/docs/manual/core/queryable-encryption/reference/shared-library/#download-the-automatic-encryption-shared-library)
  v7.0.2 or higher.

## MongoDB

You can create a new cluster on MongoDB Atlas or, for testing and local development purposes only, you can create an
ephemeral local single node replica set with the following command:

```bash
docker run --rm -d -p 27017:27017 -h $(hostname) --name mongo mongo:7.0.2 --replSet=RS && \
sleep 5 && \
docker exec mongo mongosh --quiet --eval "rs.initiate();"
```

Update the file [mongodb.properties](src%2Fmain%2Fresources%2Fmongodb.properties) with your MongoDB URI.

> Note: When you are using MongoDB Client-Side Field Level Encryption, MongoDB recommends that you store the data and
> the keys in two separate clusters in order to manage the keys independently of the data. That's because the retention
> policies should be different (GDPR). For more information,
> see [Client-Side Field Level Encryption](https://docs.mongodb.com/manual/core/security-client-side-encryption/).

## MongoDB Automatic Encryption Shared Library

Make sure to download and extract the shared library in the folder of your choice.
You will need to specify the path to this library in
the [mongodb.properties](src%2Fmain%2Fresources%2Fmongodb.properties) file.

```properties
crypt.shared.lib.path=/home/polux/Software/mongo_crypt_shared_v1-linux-x86_64-enterprise-debian11-7.0.2/lib/mongo_crypt_v1.so
```

# Getting Started

For Linux and macOS.
```bash
./mvnw spring-boot:run
```

For Windows.
```bash
mvnw.cmd spring-boot:run
```

# Test REST API

Create a `person` document:

```bash
curl -X POST http://localhost:8080/person \
  -H 'Content-Type: application/json' \
  -d '{
    "first_name": "John",
    "last_name": "Doe",
    "ssn": "123-45-6789",
    "blood_type": "A+"
}'
```

Find all persons in the database. Note that the decryption is done automatically:

```bash
curl http://localhost:8080/persons
```

Read the encrypted data in the `persons` collection:

```bash
mongosh "mongodb://localhost/mydb" --quiet --eval "db.persons.find()"
```

Result in the `persons` collection:

```json
[
  {
    _id: ObjectId("6537e9859f1b170d4cd25bee"),
    firstName: 'John',
    lastName: 'Doe',
    ssn: Binary.createFromBase64("AflGzaz/YUj6m2aENIoB50MCn1rhDllb79H17xjkUMK2obL7i038eANieCC/nO7AcaPBtpOdtqqPEvNdd9VgnC6l9QaLEIC/5w+CYPujkNxFIA37PrsqMlDeL3AsMuAgTZg=", 6),
    bloodType: Binary.createFromBase64("AvlGzaz/YUj6m2aENIoB50MCaHTxjCBlPZIck2gstfXB6yFfJ0KISjJJE24k3LXDoTv09GH+cwq+u6ApBuDU5OBkRe/6U8nPRKKcc5nirBLIzg==", 6),
    _class: 'com.mongodb.quickstart.javaspringbootcsfle.model.PersonEntity'
  }
]
```

# Author

Maxime BEUGNET <maxime@mongodb.com>

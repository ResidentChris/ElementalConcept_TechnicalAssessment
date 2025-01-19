Elemental Concept Technical Assessment
======================================

Build and run instructions
--------------------------

These instructions assume that Docker is running.

    # Build the project and run integration tests
    ./gradlew build

    # Copy the JAR file into the Docker folder
    cp build/libs/technical_assessment-0.0.1-SNAPSHOT.jar docker/java/

    # Navigate to the Docker folder
    cd docker/

    # Build Docker images
    docker compose build

    # Run Docker images
    docker compose up -d

To smoke test this running instance, open the `Elemental_Concept_Technical_Assessment.postman_collection.json` file in
Postman.

Note that Postman will probably not be able to read the `sample.txt` file without a bit of help, and so you might see
the following error message: "The request is invalid, expected a text file". If this happens, go to the Body tab, remove
the `sample.txt` file, and re-select it using 'New file from local machine'.


Confirmation criteria
---------------------


### 1. IP Address Validation

Assumptions:

* Error messages should explain the issue but should not be so specific as to make avoiding the block trivial.
* The correct way to interpret IP Address Validation Rule 2 is that the IP address should be blocked when the `isp` property is 'Amazon.com, Inc.', 'Microsoft Corporation', or 'Google LLC', and when the `hosting` property is `true`.
* If validation returns a response code other than 200, it is treated as having failed. This result in failures when the response is in the 2xx 'success' range.
* ip-api's Pro service will be used in production, so no special handling of a rate-limiting response (HTTP 429) will be required.

Check these assumptions with the PO.


#### 1.1. IP Address Validation feature flag enabled

**Given** the validation feature flag is enabled \
**When** I receive a POST request \
**Then** I validate the request's origin IP address using ip-api.com.


#### 1.2. IP Address Validation feature flag disabled

**Given** the validation feature flag is disabled \
**When** I receive a POST request \
**Then** validation is **not** performed.


#### 1.3. Block by region

##### 1.3.1. Block Chinese IP addresses

**Given** I have received a POST request \
**When** I validate the request's origin IP address using ip-api.com \
And the response code is an HTTP 200 OK \
And the `success` property of the response is 'success' \
And the `countryCode` property of the response is '**CN**' \
**Then** no other data in the request is processed \
And the response code is an HTTP 403 FORBIDDEN \
And the response body is:

    This content is not available in your region.

##### 1.3.2. Block Spanish IP addresses

**Given** I have received a POST request \
**When** I validate the request's origin IP address using ip-api.com \
And the response code is an HTTP 200 OK \
And the `success` property of the response is 'success' \
And the `countryCode` property of the response is '**ES**' \
**Then** no other data in the request is processed \
And the response code is an HTTP 403 FORBIDDEN \
And the response body is:

    This content is not available in your region.

##### 1.3.3. Block American IP addresses

**Given** I have received a POST request \
**When** I validate the request's origin IP address using ip-api.com \
And the response code is an HTTP 200 OK \
And the `success` property of the response is 'success' \
And the `countryCode` property of the response is '**US**' \
**Then** no other data in the request is processed \
And the response code is an HTTP 403 FORBIDDEN \
And the response body is:

    This content is not available in your region.


#### 1.4. Block data centres

##### 1.4.1. Block AWS

**Given** I have received a POST request \
**When** I validate the request's origin IP address using ip-api.com \
And the response code is an HTTP 200 OK \
And the `success` property of the response is 'success' \
And the `isp` property of the response is '**Amazon.com, Inc.**' \
And the `hosting` property of the response is `true \
**Then** no other data in the request is processed \
And the response code is an HTTP 403 FORBIDDEN \
And the response body is:

    This IP address has been blocked because it is a data centre.

##### 1.4.2. Block GCP

**Given** I have received a POST request \
**When** I validate the request's origin IP address using ip-api.com \
And the response code is an HTTP 200 OK \
And the `success` property of the response is 'success' \
And the `isp` property of the response is '**Google LLC**' \
And the `hosting` property of the response is `true \
**Then** no other data in the request is processed \
And the response code is an HTTP 403 FORBIDDEN \
And the response body is:

    This IP address has been blocked because it is a data centre.

##### 1.4.3. Block ADO

**Given** I have received a POST request \
**When** I validate the request's origin IP address using ip-api.com \
And the response code is an HTTP 200 OK \
And the `success` property of the response is 'success' \
And the `isp` property of the response is '**Microsoft Corporation**' \
And the `hosting` property of the response is `true \
**Then** no other data in the request is processed \
And the response code is an HTTP 403 FORBIDDEN \
And the response body is:

    This IP address has been blocked because it is a data centre.


#### 1.5. IP Address Validation unsuccessful

##### 1.5.1. Response code

**Given** I have received a POST request \
**When** I validate the request's origin IP address using ip-api.com \
And the response code is **not** an HTTP 200 OK \
**Then** no other data in the request is processed \
And the response code is an HTTP 500 INTERNAL SERVER ERROR \
And the response body is:

    IP address validation failed due to an error.

##### 1.5.2. Success property

**Given** I have received a POST request \
**When** I validate the request's origin IP address using ip-api.com \
And the response code is an HTTP 200 OK \
And the `success` property of the response is '**fail**' \
**Then** no other data in the request is processed \
And the response code is an HTTP 500 INTERNAL SERVER ERROR \
And the response body is:

    IP address validation failed due to an error.



### 2. File validation

#### 2.1. Valid file and request

**Given** I have received a POST request \
And the request has passed IP address validation \
And request has a `Content-Type` of 'multipart/form-data' \
And the multipart file contained in the request body has a content type of 'text/plain' \
And each line of the file contains seven fields, separated by a `|` character \
And each line ends with either a `\n` or an EOF (and may include trailing whitespace) \
And the first field is a UUID \
And the next four fields are Strings \
And the final two fields are doubles \
**When** I validate the file \
**Then** the file passes validation.


#### 2.2. Empty lines

**Given** I have received a POST request \
And all validation has passed until now \
And the file containes lines that are blank or contain only whitespace \
**When** I validate the file \
**Then** the file passes validation.


#### 2.3. Incorrect request body content type

**Given** I have received a POST request \
And all validation has passed until now \
And the request has a `Content-Type` that is **not** 'multipart/form-data' \
**When** I validate the file \
**Then** the response code is an HTTP 400 BAD REQUEST \
And the response body is:

    The request is invalid, expected a text file.


#### 2.4. Incorrect file content type

**Given** I have received a POST request \
And all validation has passed until now \
And the multipart file contained in the request body has a content type that is **not** 'text/plain' \
**When** I validate the file \
**Then** the response code is an HTTP 400 BAD REQUEST \
And the response body is:

    The request is invalid, expected a text file.


#### 2.5. Line validation

##### 2.5.1 Wrong number of fields in line

**Given** I have received a POST request \
And all validation has passed until now \
And a line in the file does **not** split into seven fields when split by the `|` character \
**When** I validate the file \
**Then** the response code is an HTTP 400 BAD REQUEST \
And the response body is:

    The request is invalid, expected seven fields per line but found N.

Where N is the number of fields actually found on the offending line.

##### 2.5.2 First field of line is not a UUID

**Given** I have received a POST request \
And all validation has passed until now \
And the first field of a line in the file is **not** a UUID \
**When** I validate the file \
**Then** the response code is an HTTP 400 BAD REQUEST \
And the response body is:

    The request is invalid, expected a UUID at index 0.

##### 2.5.3 Sixth or seventh field of line is not a double

**Given** I have received a POST request \
And all validation has passed until now \
And the sixth or seventh field of a line in the file is **not** a double \
**When** I validate the file \
**Then** the response code is an HTTP 400 BAD REQUEST \
And the response body is:

    The request is invalid, expected a double at index I.

Where I is the index of the offending field.


### 3. Generating an outcome file

**Given** I have received a POST request \
**When** all validation has passed \
**Then** the response code is an HTTP 200 OK \
And the `Content-Type` of the response is 'application/json' \
And the response body is a JSON array in the following format:

``` json
[
  {
    "name": "John Smith",
    "transport": "Rides A Bike",
    "top-speed": 12.1
  },
  ...
]
```

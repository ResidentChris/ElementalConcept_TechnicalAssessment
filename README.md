Confirmation criteria
=====================

## 1. IP Address Validation

Assumptions:

* Error messages should explain the issue but should not be so specific as to make avoiding the block trivial.
* The correct way to interpret IP Address Validation Rule 2 is that the IP address should be blocked when the `isp` property is 'Amazon.com, Inc.', 'Microsoft Corporation', or 'Google LLC', and when the `hosting` property is `true`.
* If validation returns a response code other than 200, it is treated as having failed. This result in failures when the response is in the 2xx 'success' range.
* ip-api's Pro service will be used in production, so no special handling of a rate-limiting response (HTTP 429) will be required.

Check these assumptions with the PO.

### 1.1. IP Address Validation feature flag enabled

**Given** the validation feature flag is enabled \
**When** I receive a POST request \
**Then** I validate the request's origin IP address using ip-api.com.

### 1.2. IP Address Validation feature flag disabled

**Given** the validation feature flag is disabled \
**When** I receive a POST request \
**Then** validation is **not** performed.


### 1.3. Block by region

#### 1.3.1. Block Chinese IP addresses

**Given** I have received a POST request \
**When** I validate the request's origin IP address using ip-api.com \
And the response code is an HTTP 200 OK \
And the `success` property of the response is 'success' \
And the `countryCode` property of the response is 'CN' \
**Then** no other data in the request is processed \
And the response code is an HTTP 403 FORBIDDEN \
And the response body is:

    This content is not available in your region.

#### 1.3.2. Block Spanish IP addresses

**Given** I have received a POST request \
**When** I validate the request's origin IP address using ip-api.com \
And the response code is an HTTP 200 OK \
And the `success` property of the response is 'success' \
And the `countryCode` property of the response is 'ES' \
**Then** no other data in the request is processed \
And the response code is an HTTP 403 FORBIDDEN \
And the response body is:

    This content is not available in your region.

#### 1.3.3. Block American IP addresses

**Given** I have received a POST request \
**When** I validate the request's origin IP address using ip-api.com \
And the response code is an HTTP 200 OK \
And the `success` property of the response is 'success' \
And the `countryCode` property of the response is 'US' \
**Then** no other data in the request is processed \
And the response code is an HTTP 403 FORBIDDEN \
And the response body is:

    This content is not available in your region.


### 1.4. Block data centres

#### 1.4.1. Block AWS

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

#### 1.4.2. Block GCP

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

#### 1.4.3. Block ADO

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


### 1.5. IP Address Validation unsuccessful (response code)

#### 1.5.1. Response code

**Given** I have received a POST request \
**When** I validate the request's origin IP address using ip-api.com \
And the response code is **not** an HTTP 200 OK \
**Then** no other data in the request is processed \
And the response code is an HTTP 500 INTERNAL SERVER ERROR \
And the response body is:

    IP address validation failed due to an error.

#### 1.5.2. Success property

**Given** I have received a POST request \
**When** I validate the request's origin IP address using ip-api.com \
And the response code is an HTTP 200 OK \
And the `success` property of the response is '**fail**' \
**Then** no other data in the request is processed \
And the response code is an HTTP 500 INTERNAL SERVER ERROR \
And the response body is:

    IP address validation failed due to an error.

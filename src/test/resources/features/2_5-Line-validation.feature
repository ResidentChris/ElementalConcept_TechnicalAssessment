Feature: 2.5. Line validation

  Background:
    Given a request is received from Hungary

  Scenario: 2.5.1. Not enough fields in line
    When the following file is uploaded:
    """
    1X1D14|John Smith|Likes Apricots|Rides A Bike|6.2|12.1
    2X2D24|Mike Smith|Likes Grape|Drives an SUV|35.0|95.5
    3X3D35|Jenny Walters|Likes Avocados|Rides A Scooter|8.5|15.3
    """
    Then the response code is an HTTP 400 BAD_REQUEST
    And the response body contains the following error message:
    """
    The request is invalid, expected seven fields per line but found 6.
    """

  Scenario: 2.5.1. Too many fields in line
    When the following file is uploaded:
    """
    18148426-89e1-11ee-b9d1-0242ac120002|1X1D14|John Smith|Likes Apricots|Rides A Bike|6.2|12.1|extra field
    3ce2d17b-e66a-4c1e-bca3-40eb1c9222c7|2X2D24|Mike Smith|Likes Grape|Drives an SUV|35.0|95.5|extra field
    1afb6f5d-a7c2-4311-a92d-974f3180ff5e|3X3D35|Jenny Walters|Likes Avocados|Rides A Scooter|8.5|15.3|extra field
    """
    Then the response code is an HTTP 400 BAD_REQUEST
    And the response body contains the following error message:
    """
    The request is invalid, expected seven fields per line but found 8.
    """

  Scenario: 2.5.2. First field of line is not a UUID
    When the following file is uploaded:
    """
    18148426-89e1-11ee-b9d1-0242ac120002|1X1D14|John Smith|Likes Apricots|Rides A Bike|6.2|12.1
    not a uuid|2X2D24|Mike Smith|Likes Grape|Drives an SUV|35.0|95.5
    1afb6f5d-a7c2-4311-a92d-974f3180ff5e|3X3D35|Jenny Walters|Likes Avocados|Rides A Scooter|8.5|15.3
    """
    Then the response code is an HTTP 400 BAD_REQUEST
    And the response body contains the following error message:
    """
    The request is invalid, expected a UUID at index 0.
    """

  Scenario: 2.5.3. Sixth field of line is not a double
    When the following file is uploaded:
    """
    18148426-89e1-11ee-b9d1-0242ac120002|1X1D14|John Smith|Likes Apricots|Rides A Bike|6.2|12.1
    3ce2d17b-e66a-4c1e-bca3-40eb1c9222c7|2X2D24|Mike Smith|Likes Grape|Drives an SUV|not a double|95.5
    1afb6f5d-a7c2-4311-a92d-974f3180ff5e|3X3D35|Jenny Walters|Likes Avocados|Rides A Scooter|8.5|15.3
    """
    Then the response code is an HTTP 400 BAD_REQUEST
    And the response body contains the following error message:
    """
    The request is invalid, expected a double at index 5.
    """

  Scenario: 2.5.3. Seventh field of line is not a double
    When the following file is uploaded:
    """
    18148426-89e1-11ee-b9d1-0242ac120002|1X1D14|John Smith|Likes Apricots|Rides A Bike|6.2|12.1
    3ce2d17b-e66a-4c1e-bca3-40eb1c9222c7|2X2D24|Mike Smith|Likes Grape|Drives an SUV|35.0|95.5
    1afb6f5d-a7c2-4311-a92d-974f3180ff5e|3X3D35|Jenny Walters|Likes Avocados|Rides A Scooter|8.5|not a double
    """
    Then the response code is an HTTP 400 BAD_REQUEST
    And the response body contains the following error message:
    """
    The request is invalid, expected a double at index 6.
    """

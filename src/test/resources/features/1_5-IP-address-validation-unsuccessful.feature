Feature: 1.5. IP Address Validation unsuccessful

  Scenario: 1.5.1. Validation unsuccessful (response code)
    Given the response code from IP address validation indicates a failure
    When the following file is uploaded:
    """
    18148426-89e1-11ee-b9d1-0242ac120002|1X1D14|John Smith|Likes Apricots|Rides A Bike|6.2|12.1
    3ce2d17b-e66a-4c1e-bca3-40eb1c9222c7|2X2D24|Mike Smith|Likes Grape|Drives an SUV|35.0|95.5
    1afb6f5d-a7c2-4311-a92d-974f3180ff5e|3X3D35|Jenny Walters|Likes Avocados|Rides A Scooter|8.5|15.3
    """
    Then the response code is an HTTP 500 INTERNAL_SERVER_ERROR
    And the response body contains the following error message:
    """
    IP address validation failed due to an error.
    """

  Scenario: 1.5.2. Validation unsuccessful (response body)
    Given the response body from IP address validation indicates a failure
    When the following file is uploaded:
    """
    18148426-89e1-11ee-b9d1-0242ac120002|1X1D14|John Smith|Likes Apricots|Rides A Bike|6.2|12.1
    3ce2d17b-e66a-4c1e-bca3-40eb1c9222c7|2X2D24|Mike Smith|Likes Grape|Drives an SUV|35.0|95.5
    1afb6f5d-a7c2-4311-a92d-974f3180ff5e|3X3D35|Jenny Walters|Likes Avocados|Rides A Scooter|8.5|15.3
    """
    Then the response code is an HTTP 500 INTERNAL_SERVER_ERROR
    And the response body contains the following error message:
    """
    IP address validation failed due to an error.
    """

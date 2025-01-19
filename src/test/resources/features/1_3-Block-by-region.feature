Feature: 1.3. Block by region

  Scenario: 1.3.1. Block Chinese IP addresses
    Given a request is received from China
    When the following file is uploaded:
    """
    18148426-89e1-11ee-b9d1-0242ac120002|1X1D14|John Smith|Likes Apricots|Rides A Bike|6.2|12.1
    3ce2d17b-e66a-4c1e-bca3-40eb1c9222c7|2X2D24|Mike Smith|Likes Grape|Drives an SUV|35.0|95.5
    1afb6f5d-a7c2-4311-a92d-974f3180ff5e|3X3D35|Jenny Walters|Likes Avocados|Rides A Scooter|8.5|15.3
    """
    Then the response code is an HTTP 403 FORBIDDEN
    And the response body contains the following error message:
    """
    This content is not available in your region.
    """
    And the following data is logged to the database:
      | requestUri                | requestTimestamp | httpResponseCode | requestIpAddress | requestCountryCode | requestIpProvider | timeLapsed |
      | http://localhost/endpoint | 1737214200000    | 403              | 127.0.0.1        | CN                 | A Chinese ISP     | 50         |

  Scenario: 1.3.2. Block Spanish IP addresses
    Given a request is received from Spain
    When the following file is uploaded:
    """
    18148426-89e1-11ee-b9d1-0242ac120002|1X1D14|John Smith|Likes Apricots|Rides A Bike|6.2|12.1
    3ce2d17b-e66a-4c1e-bca3-40eb1c9222c7|2X2D24|Mike Smith|Likes Grape|Drives an SUV|35.0|95.5
    1afb6f5d-a7c2-4311-a92d-974f3180ff5e|3X3D35|Jenny Walters|Likes Avocados|Rides A Scooter|8.5|15.3
    """
    Then the response code is an HTTP 403 FORBIDDEN
    And the response body contains the following error message:
    """
    This content is not available in your region.
    """
    And the following data is logged to the database:
      | requestUri                | requestTimestamp | httpResponseCode | requestIpAddress | requestCountryCode | requestIpProvider | timeLapsed |
      | http://localhost/endpoint | 1737214200000    | 403              | 127.0.0.1        | ES                 | A Spanish ISP     | 50         |

  Scenario: 1.3.3. Block American IP addresses
    Given a request is received from America
    When the following file is uploaded:
    """
    18148426-89e1-11ee-b9d1-0242ac120002|1X1D14|John Smith|Likes Apricots|Rides A Bike|6.2|12.1
    3ce2d17b-e66a-4c1e-bca3-40eb1c9222c7|2X2D24|Mike Smith|Likes Grape|Drives an SUV|35.0|95.5
    1afb6f5d-a7c2-4311-a92d-974f3180ff5e|3X3D35|Jenny Walters|Likes Avocados|Rides A Scooter|8.5|15.3
    """
    Then the response code is an HTTP 403 FORBIDDEN
    And the response body contains the following error message:
    """
    This content is not available in your region.
    """
    And the following data is logged to the database:
      | requestUri                | requestTimestamp | httpResponseCode | requestIpAddress | requestCountryCode | requestIpProvider | timeLapsed |
      | http://localhost/endpoint | 1737214200000    | 403              | 127.0.0.1        | US                 | An American ISP   | 50         |

  Scenario: Allow Hungarian IP addresses
    Given a request is received from Hungary
    When the following file is uploaded:
    """
    18148426-89e1-11ee-b9d1-0242ac120002|1X1D14|John Smith|Likes Apricots|Rides A Bike|6.2|12.1
    3ce2d17b-e66a-4c1e-bca3-40eb1c9222c7|2X2D24|Mike Smith|Likes Grape|Drives an SUV|35.0|95.5
    1afb6f5d-a7c2-4311-a92d-974f3180ff5e|3X3D35|Jenny Walters|Likes Avocados|Rides A Scooter|8.5|15.3
    """
    Then the response code is an HTTP 200 OK
    And the response body contains the following JSON:
    """
    [
      {
        "name": "John Smith",
        "transport": "Rides A Bike",
        "top-speed": 12.1
      },
      {
        "name": "Mike Smith",
        "transport": "Drives an SUV",
        "top-speed": 95.5
      },
      {
        "name": "Jenny Walters",
        "transport": "Rides A Scooter",
        "top-speed": 15.3
      }
    ]
    """
    And the following data is logged to the database:
      | requestUri                | requestTimestamp | httpResponseCode | requestIpAddress | requestCountryCode | requestIpProvider | timeLapsed |
      | http://localhost/endpoint | 1737214200000    | 200              | 127.0.0.1        | HU                 | A Hungarian ISP   | 50         |

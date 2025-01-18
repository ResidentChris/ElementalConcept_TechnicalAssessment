Feature: 1.4. Block data centres

  Scenario: 1.4.1. Block AWS
    Given a request is received from AWS
    When the following file is uploaded:
    """
    18148426-89e1-11ee-b9d1-0242ac120002|1X1D14|John Smith|Likes Apricots|Rides A Bike|6.2|12.1
    3ce2d17b-e66a-4c1e-bca3-40eb1c9222c7|2X2D24|Mike Smith|Likes Grape|Drives an SUV|35.0|95.5
    1afb6f5d-a7c2-4311-a92d-974f3180ff5e|3X3D35|Jenny Walters|Likes Avocados|Rides A Scooter|8.5|15.3
    """
    Then the response code is an HTTP 403 FORBIDDEN
    And the response body contains the following error message:
    """
    This IP address has been blocked because it is a data centre.
    """

  Scenario: 1.4.1. Allow Amazon (non-data-centre)
    Given a request is received from Amazon
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

  Scenario: 1.4.2. Block GCP
    Given a request is received from GCP
    When the following file is uploaded:
    """
    18148426-89e1-11ee-b9d1-0242ac120002|1X1D14|John Smith|Likes Apricots|Rides A Bike|6.2|12.1
    3ce2d17b-e66a-4c1e-bca3-40eb1c9222c7|2X2D24|Mike Smith|Likes Grape|Drives an SUV|35.0|95.5
    1afb6f5d-a7c2-4311-a92d-974f3180ff5e|3X3D35|Jenny Walters|Likes Avocados|Rides A Scooter|8.5|15.3
    """
    Then the response code is an HTTP 403 FORBIDDEN
    And the response body contains the following error message:
    """
    This IP address has been blocked because it is a data centre.
    """

  Scenario: 1.4.2. Allow Google (non-data-centre)
    Given a request is received from Google
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

  Scenario: 1.4.3. Block ADO
    Given a request is received from ADO
    When the following file is uploaded:
    """
    18148426-89e1-11ee-b9d1-0242ac120002|1X1D14|John Smith|Likes Apricots|Rides A Bike|6.2|12.1
    3ce2d17b-e66a-4c1e-bca3-40eb1c9222c7|2X2D24|Mike Smith|Likes Grape|Drives an SUV|35.0|95.5
    1afb6f5d-a7c2-4311-a92d-974f3180ff5e|3X3D35|Jenny Walters|Likes Avocados|Rides A Scooter|8.5|15.3
    """
    Then the response code is an HTTP 403 FORBIDDEN
    And the response body contains the following error message:
    """
    This IP address has been blocked because it is a data centre.
    """

  Scenario: 1.4.3. Allow Microsoft (non-data-centre)
    Given a request is received from Microsoft
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

  Scenario: 1.4.4. Allow other data centres
    Given a request is received from another data centre
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

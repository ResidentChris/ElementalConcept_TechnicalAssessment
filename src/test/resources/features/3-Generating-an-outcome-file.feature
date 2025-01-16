Feature: Outcome file API - happy path
  The API should accept an Initial File, and return an Outcome File based on the input.

  Scenario: 3. Generating an outcome file

    When the following file is uploaded:
    """
      18148426-89e1-11ee-b9d1-0242ac120002|1X1D14|John Smith|Likes Apricots|Rides A Bike|6.2|12.1
      3ce2d17b-e66a-4c1e-bca3-40eb1c9222c7|2X2D24|Mike Smith|Likes Grape|Drives an SUV|35.0|95.5
      1afb6f5d-a7c2-4311-a92d-974f3180ff5e|3X3D35|Jenny Walters|Likes Avocados|Rides A Scooter|8.5|15.3
    """

    Then the following JSON is returned:
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

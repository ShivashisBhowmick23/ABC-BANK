Feature: Transfer Operations
  As a user of the transfer service
  I want to manage transfers through various operations
  So that I can create, view, and retrieve transfers in different ways

  Scenario: Successfully create a new transfer
    Given a transfer request with valid data
    When I create the transfer
    Then the transfer is created successfully

  Scenario: Fail to create a transfer with invalid data
    Given a transfer request with invalid data
    When I create the transfer
    Then the system returns an error

  Scenario: Successfully retrieve a transfer by ID
    Given a transfer exists with ID 1
    When I retrieve the transfer by ID 1
    Then the transfer details are returned

  Scenario: Fail to retrieve a transfer by non-existing ID
    Given no transfer exists with ID 999
    When I retrieve the transfer by ID 999
    Then the system returns a "Transfer with ID 999 not found" error

  Scenario: Successfully retrieve transfers by fromAccountId
    Given transfers exist with fromAccountId 345678912
    When I retrieve transfers by fromAccountId 123456789
    Then the list of transfers is returned

  Scenario: Fail to retrieve transfers by non-existing fromAccountId
    Given no transfers exist with fromAccountId 999
    When I retrieve transfers by fromAccountId 999
    Then the system returns an empty list

  Scenario: Successfully retrieve transfers by toAccountId
    Given transfers exist with toAccountId 123456789
    When I retrieve transfers by toAccountId 345678912
    Then the list of transfers is returned

  Scenario: Fail to retrieve transfers by non-existing toAccountId
    Given no transfers exist with toAccountId 999
    When I retrieve transfers by toAccountId 999
    Then the system returns an empty list

  Scenario: Successfully retrieve transfers by type
    Given transfers exist with type "BANK"
    When I retrieve transfers by type "BANK"
    Then the list of transfers is returned

  Scenario: Fail to retrieve transfers by non-existing type
    Given no transfers exist with type "INVALID_TYPE"
    When I retrieve transfers by type "INVALID_TYPE"
    Then the system returns an empty list

  Scenario: Successfully retrieve transfers by date
    Given transfers exist on date "2024-01-04"
    When I retrieve transfers by date "2024-01-04"
    Then the list of transfers is returned

  Scenario: Successfully retrieve transfers between two dates
    Given transfers exist between dates "2024-01-09" and "2024-01-10"
    When I retrieve transfers between "2024-01-09" and "2024-01-10"
    Then the list of transfers is returned

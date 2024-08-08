@CustomerManagement
Feature: Customer Management
  Customer management operations in the bank system.

  @createSingleCustomer
  Scenario: Create a single customer
    Given the customer data is valid
    When the client requests to create a customer
    Then the response status should be 200
    And the customer is created successfully

  @createSingleCustomerWithMissingVerificationDoc
  Scenario: Create a single customer with missing verification documents
    Given the customer data is missing verification documents
    When the client requests to create a customer
    Then the response status should be 400
    And the error message should be "Verification document cannot be false."

  @createMultipleCustomers
  Scenario: Create multiple customers
    Given the customer data for multiple customers is valid
    When the client requests to create multiple customers
    Then the response status should be 200
    And the customers are created successfully

  @createMultipleCustomersWithSomeMissingVerificationDoc
  Scenario: Create multiple customers with invalid data
    Given some customer data is missing verification documents
    When the client requests to create multiple customers
    Then the response status should be 400
    And the error message should be "Some customer requests do not have verification documents."


  @getCustomerByInvalidCustomerId
  Scenario Outline: Get customer by non-existent customer ID
    Given the <customerId> does not exist
    When the client requests the customer by <customerId>
    Then the response status should be <expectedStatusCode>
    And the error message should be "Customer not found for customer ID: <customerId>"
    Examples:
      | customerId | expectedStatusCode |
      | 999        | 404                |

  @deleteCustomerByCustomerId
  Scenario: Delete customer by customer ID
    Given the customer ID exists
    When the client requests to delete the customer by customer ID
    Then the response status should be 200
    And the customer is deleted successfully

  @getAllCustomers
  Scenario: Get all customers
    When the client requests all customers
    Then the response status should be 200
    And all customer details are returned

  @UpdateCustomerByCustomerId
  Scenario Outline: Update the customer name with customer_id
    Given the customer ID exists
    When the client requests to update the customer name by <customerId>
    Then the response status should be 200
    And customer name is updated successfully
    Examples:
      | customerId |
      | 12         |

  @GetCustomerByValidCustomerId
  Scenario Outline: Fetch customer by valid customer_id
    Given the valid customer ID <customerId> exists
    When the client request to get the customer by <customerId>
    Then the response status should be 200
    And customer details are returned
    Examples:
      | customerId |
      | 8          |

  @GetCustomerByFirstLetterOfCustomerName
  Scenario Outline: Get customers by first letter of name
    Given there are customers whose names start with "<letter>"
    When the client requests customers by the first letter of name "<letter>"
    Then the response status should be <expectedStatusCode>
    And the customer details by first letter are returned

    Examples:
      | letter | expectedStatusCode |
      | a      | 200                |
      | b      | 200                |

  @GetCustomerByValidAccountId
  Scenario Outline: Fetch customer by a valid account ID
    Given the customer with account ID <accountId> exists
    When the client requests to get the customer by account ID <accountId>
    Then the response status should be 200
    And the customer details are returned

    Examples:
      | accountId |
      | 567891234 |
      | 123456789 |
      | 345678912 |
      | 678912345 |

  @GetCustomerByInvalidAccountId
  Scenario Outline: Fetch customer by an invalid account ID
    Given the customer with account ID <accountId> does not exist
    When the client requests to get the customer by account ID <accountId>
    Then the response status should be 404
    And the error message should be "Customer not found for account ID: <accountId>"

    Examples:
      | accountId |
      | 99999     |
      | 11111     |

  @GetCustomerByInvalidAccountId_Handle_Error
  Scenario Outline: Handle errors when fetching customer by account ID
    Given there is an error fetching customer with account ID <accountId>
    When the client requests to get the customer by account ID <accountId>
    Then the response status should be 404
    And the error message should be "Customer not found for account ID: <accountId>"

    Examples:
      | accountId |
      | 22222     |
      | 33333     |



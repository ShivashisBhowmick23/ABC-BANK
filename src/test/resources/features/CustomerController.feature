Feature: Customer Management
  Customer management operations in the bank system.

  @create
  Scenario: Create a single customer
    Given the customer data is valid
    When the client requests to create a customer
    Then the response status should be 200
    And the customer is created successfully

  @create @error
  Scenario: Create a single customer with missing verification documents
    Given the customer data is missing verification documents
    When the client requests to create a customer
    Then the response status should be 400
    And the error message should be "Verification document cannot be false."

  @get
  Scenario: Get customer by account ID
    Given the account ID exists
    When the client requests the customer by account ID
    Then the response status should be 200
    And the customer details are returned

  @get @error
  Scenario: Get customer by non-existent account ID
    Given the account ID does not exist
    When the client requests the customer by account ID
    Then the response status should be 404
    And the error message should be "Customer not found for account ID: <accountId>"

  @create @multiple
  Scenario: Create multiple customers
    Given the customer data for multiple customers is valid
    When the client requests to create multiple customers
    Then the response status should be 200
    And the customers are created successfully

  @create @multiple @error
  Scenario: Create multiple customers with invalid data
    Given some customer data is missing verification documents
    When the client requests to create multiple customers
    Then the response status should be 400
    And the error message should be "Some customer requests do not have verification documents."

  @get
  Scenario: Get customer by customer ID
    Given the customer ID exists
    When the client requests the customer by customer ID
    Then the response status should be 200
    And the customer details are returned

  @get @error
  Scenario: Get customer by non-existent customer ID
    Given the customer ID does not exist
    When the client requests the customer by customer ID
    Then the response status should be 404
    And the error message should be "Customer not found for customer ID: <customerId>"

  @update
  Scenario: Update customer by customer ID
    Given the customer ID exists
    And the updated customer data is valid
    When the client requests to update the customer by customer ID
    Then the response status should be 200
    And the customer is updated successfully

  @update @error
  Scenario: Update non-existent customer by customer ID
    Given the customer ID does not exist
    And the updated customer data is valid
    When the client requests to update the customer by customer ID
    Then the response status should be 404
    And the error message should be "Customer not found for customer ID: <customerId>"

  @delete
  Scenario: Delete customer by customer ID
    Given the customer ID exists
    When the client requests to delete the customer by customer ID
    Then the response status should be 200
    And the customer is deleted successfully

  @delete @error
  Scenario: Delete non-existent customer by customer ID
    Given the customer ID does not exist
    When the client requests to delete the customer by customer ID
    Then the response status should be 404
    And the error message should be "Customer not found for ID: <customerId>"

  @get @filter
  Scenario Outline: Get customers by first letter of name
    Given there are customers whose names start with "<letter>"
    When the client requests customers by the first letter of name "<letter>"
    Then the response status should be 200
    And the customer details are returned

    Examples:
      | letter |
      | A      |
      | B      |

  @get @all
  Scenario: Get all customers
    When the client requests all customers
    Then the response status should be 200
    And the customer details are returned

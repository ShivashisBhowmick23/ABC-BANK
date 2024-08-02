@CustomerManagement
Feature: Customer Management
  Customer management operations in the bank system.

  @createCustomer
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


  @get @error
  Scenario Outline: Get customer by non-existent account ID
    Given the account ID <accountId> does not exist
    When the client requests the customer by account ID
    Then the response status should be 404
    And the error message should be "Customer not found for account ID: <accountId>"
    Examples:
      | accountId |
      | 999       |

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


  @get @error
  Scenario Outline: Get customer by non-existent customer ID
    Given the <customerId> does not exist
    When the client requests the customer by <customerId>
    Then the response status should be <expectedStatusCode>
    And the error message should be "Customer not found for customer ID: <customerId>"
    Examples:
      | customerId | expectedStatusCode |
      | 999        | 404                |

  @delete
  Scenario: Delete customer by customer ID
    Given the customer ID exists
    When the client requests to delete the customer by customer ID
    Then the response status should be 200
    And the customer is deleted successfully

  @get @all
  Scenario: Get all customers
    When the client requests all customers
    Then the response status should be 200
    And all customer details are returned



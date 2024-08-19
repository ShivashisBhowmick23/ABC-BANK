Feature: Transaction Management

  @CreateTransaction
  Scenario: Create a new transaction
    Given the transaction data is valid
    When the client requests to create a transaction
    Then the response status should 200
    And the response body should contain transaction ID

  @FetchTransactionByAccountId
  Scenario: Fetch transactions by account ID
    Given the transaction data exists with account ID 123456789
    When the client requests to fetch transactions by account ID 123456789
    Then the transactions for account ID 123456789 are returned

  @FetchTransactionByDate
  Scenario Outline: Fetch transactions by date
    Given the transaction data exists with date "<date>"
    When the client requests to fetch transactions by date "<date>"
    Then the transactions for date "<date>" are returned
    Examples:
      | date       |
      | 2024-01-01 |

  @InvalidTransactionId
  Scenario Outline: Error handling for transaction operations
    Given the transaction with ID <transactionId> does not exist
    When the client requests to fetch transaction by ID <transactionId>
    Then the response status should 500
    And the error message should "An error occurred: Transaction not found for ID <transactionId>"

    Examples:
      | transactionId |
      | 99999         |
      | 88888         |

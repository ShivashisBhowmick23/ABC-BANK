Feature: Create a new transaction

  Scenario Outline: Creating a transaction
    Given transaction request with accountId <accountId>, amount <amount>, and transactionType "<transactionType>"
    When the client requests to create a transaction
    Then the response status code should be <statusCode>
    And the response should contain a transaction with accountId <accountId>, amount <amount>, and transactionType "<transactionType>"

    Examples:
      | accountId | amount | transactionType | statusCode |
      | 123456789 | 1000.0 | DEPOSIT         | 200        |
      | 123456789 | 500.0  | WITHDRAWAL      | 200        |

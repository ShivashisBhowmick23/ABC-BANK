package com.exercise.boot.util;

import org.springframework.stereotype.Component;

@Component
public class EmailContentBuilder {

    // Method to prepare content for account creation
    public String buildAccountCreationEmailContent(String customerName, String accountNumber) {
        return "Dear " + customerName + ",\n\n" +
                "Your account has been successfully created with account number: " + accountNumber + ".\n" +
                "Thank you for choosing ABC Bank.\n\n" +
                "Best regards,\nABC Bank Team";
    }

    // Method to prepare content for transaction activity
    public String buildTransactionEmailContent(String customerName, String transactionType, double amount, String accountNumber) {
        return "Dear " + customerName + ",\n\n" +
                "Your account number " + accountNumber + " has been debited/credited with " + amount + " due to a " + transactionType + ".\n" +
                "Thank you for using ABC Bank services.\n\n" +
                "Best regards,\nABC Bank Team";
    }

    // Method to prepare content for transfer activity
    public String buildTransferEmailContent(double amount, int fromAccount, int toAccount) {
        return "Dear  Customer"+".\n\n" +".\n"+
                "You have successfully transferred " + amount + " from your account " + fromAccount +
                " to account " + toAccount + ".\n" +
                "If you did not perform this transaction, please contact us immediately.\n\n" +
                "Thank you for banking with ABC Bank.\n\n" +
                "Best regards,\nABC Bank Team";
    }
}

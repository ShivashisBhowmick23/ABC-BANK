package com.exercise.boot.constants;

public class BankURLConstant {
    public static final String CUSTOMER_SERVICE = "/bank/customer";
    public static final String ADD_CUSTOMER = CUSTOMER_SERVICE + "/add-customer";
    public static final String GET_CUSTOMER_BY_ACC_ID = CUSTOMER_SERVICE + "/{accountId}";
    public static final String TRANSACTION_SERVICE = "/bank/transaction";
    public static final String CREATE_TRANSACTION = TRANSACTION_SERVICE + "/create";
    public static final String GET_TRANSACTION_BY_ACC_ID = TRANSACTION_SERVICE + "/{accountId}";
    public static final  String ADD_MULTIPLE_CUSTOMERS = CUSTOMER_SERVICE+"/add-customers" ;
}

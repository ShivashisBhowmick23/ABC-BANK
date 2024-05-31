package com.exercise.boot.constants;

import org.springframework.stereotype.Component;

@Component
public class BankURLConstant {
    public static final String CUSTOMER_SERVICE = "/bank";
    public static final String TRANSACTION_SERVICE = "/transaction";

    public static final String ADD_CUSTOMER = CUSTOMER_SERVICE + "/add" + "/single-customer";
    public static final String GET_CUSTOMER_BY_ACC_ID = CUSTOMER_SERVICE + "/fetch" + "/{accountId}";
    public static final String GET_CUSTOMER_BY_CUST_ID = CUSTOMER_SERVICE + "/fetch" + "/{custId}";


    public static final String CREATE_TRANSACTION = TRANSACTION_SERVICE + "/create";
    public static final String GET_TRANSACTION_BY_ACC_ID = TRANSACTION_SERVICE + "/fetch" + "/{accountId}";
    public static final String ADD_MULTIPLE_CUSTOMERS = CUSTOMER_SERVICE + "/add" + "/multiple-customers";
}

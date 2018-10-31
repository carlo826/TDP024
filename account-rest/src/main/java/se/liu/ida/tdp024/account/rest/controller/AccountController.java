
package se.liu.ida.tdp024.account.rest.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import se.liu.ida.tdp024.account.data.impl.db.facade.AccountEntityFacadeDB;
import se.liu.ida.tdp024.account.data.impl.db.facade.TransactionEntityFacadeDB;
import se.liu.ida.tdp024.account.logic.api.facade.AccountLogicFacade;
import se.liu.ida.tdp024.account.logic.api.facade.TransactionLogicFacade;
import se.liu.ida.tdp024.account.logic.impl.facade.AccountLogicFacadeImpl;
import se.liu.ida.tdp024.account.logic.impl.facade.TransactionLogicFacadeImpl;
import se.liu.ida.tdp024.account.logic.impl.utils.ApiHelperImpl;

@RestController
public class AccountController {
    
    private final AccountLogicFacade accountLogicFacade = new AccountLogicFacadeImpl(new AccountEntityFacadeDB(), new ApiHelperImpl());
    private final TransactionLogicFacade transactionLogicFacade = new TransactionLogicFacadeImpl(new TransactionEntityFacadeDB());

    @RequestMapping("account-rest/account/create")
    public String accountCreate(@RequestParam(value="accounttype") String accountType, 
                                @RequestParam(value="person") String personKey, 
                                @RequestParam(value="bank") String bankKey) {
        if(!"CHECK".equals(accountType) && !"SAVINGS".equals(accountType))
            throw new IllegalArgumentException("AccountType is not valid");
        if(personKey == null || "".equals(personKey))
            throw new IllegalArgumentException("PersonKey is not valid");
        if(bankKey == null || "".equals(bankKey))
            throw new IllegalArgumentException("BankKey is not valid");
        return accountLogicFacade.create(accountType, personKey, bankKey) ? "OK" : "FAILED";
    }
    
    @RequestMapping("account-rest/account/find/person")
    public String accountPersonFind(@RequestParam(value="person") String personKey) {
        if(personKey == null || "".equals(personKey))
            throw new IllegalArgumentException("PersonKey is not valid");
        Gson gsonBuilder = new GsonBuilder().create();
        return gsonBuilder.toJson(accountLogicFacade.find(personKey));
    }
    
    @RequestMapping("account-rest/account/debit")
    public String accountDebit(@RequestParam(value="id") long id,
                               @RequestParam(value="amount") int amount) {
        if (id < 0) 
            throw new IllegalArgumentException("ID not valid");
        if (amount < 0) 
            throw new IllegalArgumentException("Amount not valid");
        return transactionLogicFacade.debit(id, amount) ? "OK" : "FAILED";
    }
    
    @RequestMapping("account-rest/account/credit")
    public String accountCredit(@RequestParam(value="id") long id,
                                @RequestParam(value="amount") int amount) {
        if (id < 0) 
            throw new IllegalArgumentException("ID not valid");
        if (amount < 0) 
            throw new IllegalArgumentException("Amount not valid");
        return transactionLogicFacade.credit(id, amount) ? "OK" : "FAILED";
    }
    
    @RequestMapping("account-rest/account/transactions")
    public String transactions(@RequestParam(value="id") long id) {
        if (id < 0) 
            throw new IllegalArgumentException("ID not valid");
        Gson gsonBuilder = new GsonBuilder().create();
        return gsonBuilder.toJson(transactionLogicFacade.transactions(id));    
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public String handleMissingParams(MissingServletRequestParameterException ex) {
        String name = ex.getParameterName();
        System.out.println("Parameter: " +name+ " is missing");
        return "FAILED";
    }
    
    
}


package endpoints;

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
        return accountLogicFacade.create(accountType, personKey, bankKey) ? "OK" : "FAILED";
    }
    
    @RequestMapping("account-rest/account/find/person")
    public String accountPersonFind(@RequestParam(value="person") String personKey) {
        Gson gsonBuilder = new GsonBuilder().create();
        return gsonBuilder.toJson(accountLogicFacade.find(personKey));
    }
    
    @RequestMapping("account-rest/account/debit")
    public String accountDebit(@RequestParam(value="id") int id,
                               @RequestParam(value="amount") int amount) {
        return transactionLogicFacade.debit(id, amount) ? "OK" : "FAILED";
    }
    
    @RequestMapping("account-rest/account/credit")
    public String accountCredit(@RequestParam(value="id") int id,
                                @RequestParam(value="amount") int amount) {
        return transactionLogicFacade.credit(id, amount) ? "OK" : "FAILED";
    }
    
    @RequestMapping("account-rest/account/transactions")
    public String accountPersonFind(@RequestParam(value="id") int id) {
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

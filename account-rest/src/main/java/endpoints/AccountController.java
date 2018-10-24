
package endpoints;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.ArrayList;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.impl.db.facade.AccountEntityFacadeDB;
import se.liu.ida.tdp024.account.logic.api.facade.AccountLogicFacade;
import se.liu.ida.tdp024.account.logic.impl.facade.AccountLogicFacadeImpl;

@RestController
public class AccountController {
    
    private final AccountLogicFacade accountLogicFacade = new AccountLogicFacadeImpl(new AccountEntityFacadeDB());
    
    @RequestMapping("/account/create")
    public String accountCreate(@RequestParam(value="accounttype") String accountType, 
                                @RequestParam(value="person") String personKey, 
                                @RequestParam(value="bank") String bankKey) {
        return accountLogicFacade.create(accountType, personKey, bankKey) ? "OK" : "FAILED";
    }
    
    @RequestMapping("/account/find/person")
    public String accountPersonFind(@RequestParam(value="person") String personKey) {
        Gson gsonBuilder = new GsonBuilder().create();
        return gsonBuilder.toJson(accountLogicFacade.find(personKey));
    }
    
    @RequestMapping("/account/debit")
    public String accountDebit(@RequestParam(value="id") int id,
                               @RequestParam(value="amount") int amount) {
        return "OK";
    }
    
    @RequestMapping("/account/credit")
    public String accountCredit(@RequestParam(value="id") int id,
                                @RequestParam(value="amount") int amount) {
        return "OK";
    }
    
    @RequestMapping("/account/transactions")
    public String accountPersonFind(@RequestParam(value="id") int id) {
        return "JSON";
    }
    
}

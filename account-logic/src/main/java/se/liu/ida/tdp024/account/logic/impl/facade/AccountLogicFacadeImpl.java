package se.liu.ida.tdp024.account.logic.impl.facade;

import java.util.ArrayList;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.facade.AccountEntityFacade;
import se.liu.ida.tdp024.account.logic.api.facade.AccountLogicFacade;

public class AccountLogicFacadeImpl implements AccountLogicFacade {
    
    private final AccountEntityFacade accountEntityFacade;
    
    public AccountLogicFacadeImpl(AccountEntityFacade accountEntityFacade) {
        this.accountEntityFacade = accountEntityFacade;
    }

    @Override
    public boolean create(String accountType, String personKey, String bankKey) {
        /**
         * Find person and bank from micro-services with given keys
         * If keys are valid, create an account in data layer, return true
         * If keys are invalid or account already exists, return false
         */
        try {
            accountEntityFacade.create(Account.Type.valueOf(accountType), personKey, bankKey);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;      
        }
  
    }

    @Override
    public ArrayList<Account> find(String personKey) {
        try {
            return new ArrayList(accountEntityFacade.find(personKey));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ArrayList<>();
        }
    }
    
}

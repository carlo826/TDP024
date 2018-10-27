package se.liu.ida.tdp024.account.logic.impl.facade;

import java.util.ArrayList;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.facade.AccountEntityFacade;
import se.liu.ida.tdp024.account.logic.api.facade.AccountLogicFacade;
import se.liu.ida.tdp024.account.logic.api.utils.ApiHelper;

public class AccountLogicFacadeImpl implements AccountLogicFacade {

    private final AccountEntityFacade accountEntityFacade;
    private final ApiHelper apiHelper;
    
    public AccountLogicFacadeImpl(AccountEntityFacade accountEntityFacade, ApiHelper apiHelper) {
        this.accountEntityFacade = accountEntityFacade;
        this.apiHelper = apiHelper;
    }

    @Override
    public boolean create(String accountType, String personKey, String bankKey) {
        /**
         * Find person and bank from micro-services with given keys
         * If keys are valid, create an account in data layer, return true
         * If keys are invalid or account already exists, return false
         */
        
        String person;
        String bank;
        
        try {
            person = apiHelper.getPerson(personKey);
            if ("null".equals(person))
                throw new IllegalArgumentException("PersonKey: "+ personKey +", does not exist");
            bank = apiHelper.getBank(bankKey);
            if ("null".equals(bank))
                throw new IllegalArgumentException("BankKey: "+ bankKey +", does not exist");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        
        
        try {
            accountEntityFacade.create(Account.Type.valueOf(accountType), personKey, bank);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;      
        }
  
    }

    @Override
    public ArrayList<Account> find(String personKey) {
        return new ArrayList(accountEntityFacade.find(personKey));
    }
    
}

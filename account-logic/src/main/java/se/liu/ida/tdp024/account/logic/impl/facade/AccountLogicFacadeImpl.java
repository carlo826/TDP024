package se.liu.ida.tdp024.account.logic.impl.facade;

import java.util.ArrayList;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.facade.AccountEntityFacade;
import se.liu.ida.tdp024.account.logic.api.facade.AccountLogicFacade;
import se.liu.ida.tdp024.account.logic.api.utils.HTTPGet;

public class AccountLogicFacadeImpl implements AccountLogicFacade {
    
    private final String PERSON_ENDPOINT = "http://localhost:8060/person/";
    private final String BANK_ENDPOINT = "http://localhost:8070/bank/";

    private final AccountEntityFacade accountEntityFacade;
    private final HTTPGet httpGet;
    
    public AccountLogicFacadeImpl(AccountEntityFacade accountEntityFacade, HTTPGet httpGet) {
        this.accountEntityFacade = accountEntityFacade;
        this.httpGet = httpGet;
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
            person = httpGet.get(PERSON_ENDPOINT + "/find.key?key=" + personKey);
            if ("null".equals(person))
                throw new IllegalArgumentException("PersonKey does not exist");
            bank = httpGet.get(BANK_ENDPOINT + "/find.name?name=" + bankKey);
            if ("null".equals(bank))
                throw new IllegalArgumentException("BankKey does not exist");
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
        try {
            return new ArrayList(accountEntityFacade.find(personKey));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ArrayList<>();
        }
    }
    
}

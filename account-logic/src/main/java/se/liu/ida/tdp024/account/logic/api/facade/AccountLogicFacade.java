package se.liu.ida.tdp024.account.logic.api.facade;

import java.util.ArrayList;
import se.liu.ida.tdp024.account.data.api.entity.Account;


public interface AccountLogicFacade {
    boolean create(String accountType, String personKey, String bankKey);
    ArrayList<Account> find(String personKey);
}

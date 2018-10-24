package se.liu.ida.tdp024.account.data.api.facade;

import java.util.List;
import se.liu.ida.tdp024.account.data.api.entity.Account;

public interface AccountEntityFacade {
    Account create(Account.Type accountType, String personKey, String bankKey);
    List<Account> find(String personKey);
}

package se.liu.ida.tdp024.account.data.test.facade;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.facade.AccountEntityFacade;
import se.liu.ida.tdp024.account.data.api.util.StorageFacade;
import se.liu.ida.tdp024.account.data.impl.db.facade.AccountEntityFacadeDB;
import se.liu.ida.tdp024.account.data.impl.db.util.StorageFacadeDB;

public class AccountEntityFacadeTest {
    
    //---- Unit under test ----//
    private AccountEntityFacade accountEntityFacade;
    private StorageFacade storageFacade;
    
    @Before
    public void setup() {
        accountEntityFacade = new AccountEntityFacadeDB();
        storageFacade = new StorageFacadeDB();
    }
    
    @After
    public void clean() {
        storageFacade.emptyStorage();
    }
    
    @Test
    public void testGoodCreate() {
        Account acc = accountEntityFacade.create(Account.Type.CHECK, "personKey", "bankKey");
        Assert.assertTrue("bankKey".equals(acc.getBankKey()));
        Assert.assertTrue("personKey".equals(acc.getPersonKey()));
        Assert.assertTrue(acc.getAccountType() == Account.Type.CHECK);
        Assert.assertTrue(acc.getHoldings() == 0);
        Assert.assertTrue(acc.getID() == 1);
    }
    
    @Test
    public void testCreateBadName() {
        Account acc = accountEntityFacade.create(Account.Type.CHECK, null, "bankKey");
        Assert.assertTrue(acc == null);
    }
    
    @Test
    public void testCreateBadBank() { 
        Account acc = accountEntityFacade.create(Account.Type.CHECK, "personKey", "");
        Assert.assertTrue(acc == null);
    }
    
    @Test
    public void testFindAll() {
        accountEntityFacade.create(Account.Type.CHECK, "personKey", "bankKey");
        accountEntityFacade.create(Account.Type.SAVINGS, "personKey", "bankKey");
        Assert.assertTrue(accountEntityFacade.find("personKey").size() == 2);
    }
    
    @Test
    public void testFindNone() {
        Assert.assertTrue(accountEntityFacade.find("-----").isEmpty());
    }

}
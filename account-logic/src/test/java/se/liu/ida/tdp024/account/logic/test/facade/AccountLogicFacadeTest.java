package se.liu.ida.tdp024.account.logic.test.facade;

import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.util.StorageFacade;
import se.liu.ida.tdp024.account.data.impl.db.facade.AccountEntityFacadeDB;
import se.liu.ida.tdp024.account.data.impl.db.util.StorageFacadeDB;
import se.liu.ida.tdp024.account.logic.api.facade.AccountLogicFacade;
import se.liu.ida.tdp024.account.logic.impl.facade.AccountLogicFacadeImpl;
import se.liu.ida.tdp024.account.logic.impl.utils.ApiHelperImpl;

public class AccountLogicFacadeTest {


    //--- Unit under test ---//
    public AccountLogicFacade accountLogicFacade;
    public StorageFacade storageFacade;

    @Before
    public void setup() {
        accountLogicFacade = new AccountLogicFacadeImpl(new AccountEntityFacadeDB(), new ApiHelperImpl());
        storageFacade = new StorageFacadeDB();
    }
    
    @After
    public void clean() {
      if (storageFacade != null)
        storageFacade.emptyStorage();
    }
    
    @Test
    public void testCreateCheck() {
        accountLogicFacade.create("CHECK", "1", "SWEDBANK");
        Account acc = accountLogicFacade.find("1").get(0);
        Assert.assertTrue(acc.getAccountType() == Account.Type.CHECK);
    }
    
    @Test
    public void testCreateSavings() {
        accountLogicFacade.create("SAVINGS", "1", "SWEDBANK");
        Account acc = accountLogicFacade.find("1").get(0);
        Assert.assertTrue(acc.getAccountType() == Account.Type.SAVINGS);
    }
    
    
    @Test
    public void testCreateBadPerson() {
        boolean res = accountLogicFacade.create("CHECK", "FRANS", "Swedbank");
        Assert.assertFalse(res);
    }
    
    @Test
    public void testCreateBadBank() {
        boolean res = accountLogicFacade.create("CHECK", "1", "EnDåligBank");
        Assert.assertFalse(res);
    }
    
    @Test
    public void testCreateBadType() {
        boolean res = accountLogicFacade.create("CREDITCARD", "1", "SWEDBANK");
        Assert.assertFalse(res);
    }
    
    
    @Test
    public void testFind() {
        accountLogicFacade.create("CHECK", "2", "SWEDBANK");
        List<Account> accs = accountLogicFacade.find("2");
        Assert.assertTrue(!accs.isEmpty() && accs.get(0).getAccountType() == Account.Type.CHECK);
    }
    
    @Test
    public void testFindNotExist() {
        List<Account> accs = accountLogicFacade.find("00#*ÂÄWDÅ^Ö");
        Assert.assertTrue(accs.isEmpty());
    }
}

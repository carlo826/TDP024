/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.liu.ida.tdp024.account.rest.test.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.Arrays;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.bind.MissingServletRequestParameterException;
import se.liu.ida.tdp024.account.data.impl.db.entity.AccountDB;
import se.liu.ida.tdp024.account.data.impl.db.entity.TransactionDB;
import se.liu.ida.tdp024.account.rest.controller.AccountController;

/**
 *
 * @author frazz
 */
public class AccountControllerTest {
    

    //--- Unit under test ---//
    
    @MockBean
    private AccountController accountController;
    private long id = 1;
    
    @Before
    public void setup() {
        accountController = new AccountController();
        accountController.accountCreate("CHECK", "1", "SWEDBANK");
    }
    
    @After
    public void clean() { }
    
    @Test
    public void testGoodCreate() {
        String res = accountController.accountCreate("CHECK", "2", "SWEDBANK");
        assertEquals("OK", res);
    }
    
    @Test
    public void testBadCreate() {
        String res = accountController.accountCreate("CHECK", "CARL", "SWEDBANK");
        assertEquals("FAILED", res);
    }
    
    @Test
    public void testGoodDebitAndCredit() {
        String cred = accountController.accountCredit(id, 200);
        String deb = accountController.accountDebit(id, 200);
        assertEquals("OK", cred);
        assertEquals("OK", deb);
    }
    
    @Test
    public void testBadDebit() {
        String res = accountController.accountDebit(id, 200);
        assertEquals("FAILED", res);
    }
    
    @Test
    public void testBadCredit() {
        String res = accountController.accountCredit(9999999, 200);
        assertEquals("FAILED", res);
    }
    
    @Test
    public void testFind() {
        Gson gsonBuilder = new GsonBuilder().create();
        String res = accountController.accountPersonFind("1");
        List<AccountDB> accs = Arrays.asList(gsonBuilder.fromJson(res, AccountDB[].class));
        assertEquals(1, accs.get(0).getID());
    }
    
    @Test
    public void testFindNone() {
        String res = accountController.accountPersonFind("912___3012");
        assertEquals("[]", res);
    }
        
    @Test
    public void testTransactions() {
        accountController.accountCredit(id, 200);
        String res = accountController.transactions(id);
        Assert.assertFalse("[]".equals(res));
    }
    
    @Test
    public void testTransactionsNone() {
        String res = accountController.accountPersonFind("912___3012");
        assertEquals("[]", res);
    }
    
    @Test
    public void testBadParams() {
        String res = accountController.handleMissingParams(new MissingServletRequestParameterException("id", "Integer"));
        assertEquals("FAILED", res);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testInvalidAccountType() {
        accountController.accountCreate("ASD", "2", "SWEDBANK");
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testCreateInvalidPersonKey() {
        accountController.accountCreate("CHECK", "", "SWEDBANK");
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testCreateInvalidBankKey() {
        accountController.accountCreate("CHECK", "2", "");
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testFindInvalidPersonKey() {
        accountController.accountPersonFind("");
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testDebitInvalidID() {
        accountController.accountDebit(-1, 200);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testDebitInvalidAmount() {
        accountController.accountDebit(id, -200);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testCreditInvalidID() {
        accountController.accountCredit(-1, 200);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testCreditInvalidAmount() {
        accountController.accountCredit(id, -200);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testTransactionsInvalidID() {
        accountController.transactions(-1);
    }
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.liu.ida.tdp024.account.logic.impl.facade;

import java.util.ArrayList;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;
import se.liu.ida.tdp024.account.data.api.facade.TransactionEntityFacade;
import se.liu.ida.tdp024.account.logic.api.facade.TransactionLogicFacade;

/**
 *
 * @author frazz
 */
public class TransactionLogicFacadeImpl implements TransactionLogicFacade {
    
    private final TransactionEntityFacade transactionEntityFacade;
    
    public TransactionLogicFacadeImpl(TransactionEntityFacade transactionEntityFacade) {
        this.transactionEntityFacade = transactionEntityFacade;
    }
    
    @Override
    public ArrayList<Transaction> transactions(long id) {
        return new ArrayList<>(transactionEntityFacade.transactions(id));
    }

    @Override
    public boolean credit(long id, int amount) {
        return transactionEntityFacade.credit(id, amount) == Transaction.Status.OK;
    }

    @Override
    public boolean debit(long id, int amount) {
        return transactionEntityFacade.debit(id, amount) == Transaction.Status.OK;
    }
}

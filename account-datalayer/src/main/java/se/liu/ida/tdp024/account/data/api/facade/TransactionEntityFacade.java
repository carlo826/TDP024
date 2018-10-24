package se.liu.ida.tdp024.account.data.api.facade;

import java.util.List;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author frazz
 */
public interface TransactionEntityFacade {
    List<Transaction> transactions(long id);
    Transaction.Status credit(long id, int amount);
    Transaction.Status debit(long id, int amount);
}

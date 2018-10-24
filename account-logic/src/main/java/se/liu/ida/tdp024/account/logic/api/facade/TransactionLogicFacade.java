/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.liu.ida.tdp024.account.logic.api.facade;

import java.util.ArrayList;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;

/**
 *
 * @author frazz
 */
public interface TransactionLogicFacade {
    ArrayList<Transaction> transactions (long id);
    boolean credit(long id, int amount);
    boolean debit(long id, int amount);
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.liu.ida.tdp024.account.data.impl.db.facade;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.LockModeType;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;
import se.liu.ida.tdp024.account.data.api.facade.TransactionEntityFacade;
import se.liu.ida.tdp024.account.data.impl.db.entity.AccountDB;
import se.liu.ida.tdp024.account.data.impl.db.entity.TransactionDB;
import se.liu.ida.tdp024.account.data.impl.db.util.EMF;

/**
 *
 * @author frazz
 */
public class TransactionEntityFacadeDB implements TransactionEntityFacade {

    private final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    
    @Override
    public List<Transaction> transactions(long id) {
        /**
         * Returns all transactions associated with given id.
         */
        EntityManager em = EMF.getEntityManager();
        try {
            Account account = em.find(AccountDB.class, id);
            return em.createQuery(
                    "SELECT t FROM TransactionDB t WHERE t.account = :account ", Transaction.class)
                    .setParameter("account", account)
                    .getResultList();
        } finally {
            em.close();
        }    
    }

    @Override
    public Transaction.Status credit(long id, int amount) {
        EntityManager em = EMF.getEntityManager();
        EntityTransaction tx = null;
        Account account = em.find(AccountDB.class, id);
        Transaction.Status status = Transaction.Status.FAILED;
        try {
            tx = em.getTransaction();
            tx.begin();
            em.lock(account, LockModeType.PESSIMISTIC_WRITE);
            if (account == null)
                throw new IllegalArgumentException("Account does not exist with ID: "+ id);
            if (amount <= 0)
                throw new IllegalArgumentException("Amount must be positive");
            account.setHoldings(account.getHoldings()+amount);
            tx.commit();
            status = Transaction.Status.OK;
        } catch(RuntimeException e){
            if (tx != null && tx.isActive())
                tx.rollback();
            System.out.println(e.getMessage());
        } finally {
            em.close();
        }
        
        Transaction transaction = new TransactionDB(
                id, 
                Transaction.Type.CREDIT, 
                amount, 
                status, 
                dateFormat.format(Calendar.getInstance().getTime()), 
                account
        );
        insertIntoDatabase(transaction);
        return status;
    }

    @Override
    public Transaction.Status debit(long id, int amount) {
        EntityManager em = EMF.getEntityManager();
        EntityTransaction tx = null;
        Account account = em.find(AccountDB.class, id);
        Transaction.Status status = Transaction.Status.FAILED;
        try {
            tx = em.getTransaction();
            tx.begin();
            em.lock(account, LockModeType.PESSIMISTIC_WRITE);
            if (account == null)
                throw new IllegalArgumentException("Account does not exist with ID: "+ id);
            if (amount > account.getHoldings())
                throw new IllegalArgumentException("Account does not have enough holdings to debit "+amount+" monies"); 
            if (amount <= 0)
                throw new IllegalArgumentException("Amount must be positive"); 
            account.setHoldings(account.getHoldings()-amount);
            tx.commit();
            status = Transaction.Status.OK;
        } catch(RuntimeException e){
            if (tx != null && tx.isActive())
                tx.rollback();
            System.out.println(e.getMessage());
        } finally {
            em.close();
        }
        
        Transaction transaction = new TransactionDB(
                id, 
                Transaction.Type.DEBIT, 
                amount, 
                status, 
                dateFormat.format(Calendar.getInstance().getTime()), 
                account
        );
        insertIntoDatabase(transaction);
        return status;
    }
    
    private void insertIntoDatabase (Transaction transaction) {
        EntityManager em = EMF.getEntityManager();
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            em.persist(transaction);
            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive())
                tx.rollback();
            System.out.println(e.getMessage());
        } finally {
            em.close();
        }
    }
}

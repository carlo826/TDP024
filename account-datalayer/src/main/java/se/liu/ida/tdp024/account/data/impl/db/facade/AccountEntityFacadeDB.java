package se.liu.ida.tdp024.account.data.impl.db.facade;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.facade.AccountEntityFacade;
import se.liu.ida.tdp024.account.data.impl.db.entity.AccountDB;
import se.liu.ida.tdp024.account.data.impl.db.util.EMF;
import se.liu.ida.tdp024.account.data.impl.db.util.StorageFacadeDB;

public class AccountEntityFacadeDB implements AccountEntityFacade {

    @Override
    public Account create(Account.Type accountType, String personKey, String bankKey) {
        /**
         * Creates and return a new account.
         */
        EntityManager em = EMF.getEntityManager();
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            Account account = new AccountDB();
            account.setAccountType(accountType);
            account.setPersonKey(personKey);
            account.setBankKey(bankKey); 
            em.persist(account);
            tx.commit();
            return account;
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive())
                tx.rollback();
            System.out.println(e.getMessage());
        } finally {
            em.close();
        }
        return null;
    }

    @Override
    public List<Account> find(String personKey) {
        /**
         * Returns all accounts associated with given person.
         */
        EntityManager em = EMF.getEntityManager();
        try {
            return em.createQuery(
                    "SELECT a FROM AccountDB a WHERE a.personKey = :personKey ", Account.class)
                    .setParameter("personKey", personKey)
                    .getResultList();
        } finally {
            em.close();
        }
    }
}

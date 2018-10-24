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
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            Account account = new AccountDB();
            account.setAccountType(accountType);
            account.setPersonKey(personKey);
            account.setBankKey(bankKey); 
            em.persist(account);
            tx.commit();
            return account;
        } finally {
            em.close();
        }
    }

    @Override
    public List<Account> find(String personKey) {
        /**
         * Returns all accounts associated with given person.
         */
        EntityManager em = EMF.getEntityManager();
        try {
            return em.createQuery(
                    "SELECT a FROM AccountDB a WHERE a.personKey LIKE :personKey ", Account.class)
                    .setParameter("personKey", personKey)
                    .getResultList();
        } finally {
            em.close();
        }
    }
    
}

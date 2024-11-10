package com.jd2.moviebase.repository;

import com.jd2.moviebase.exception.MovieDbRepositoryOperationException;
import com.jd2.moviebase.model.Account;

import java.util.Optional;

import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AccountRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public AccountRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    public Account findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            Account account = session.find(Account.class, id);
            return Optional.ofNullable(account)
                    .orElseThrow(() -> new MovieDbRepositoryOperationException("Account with ID " + id + " not found"));
        }
    }

    public Account findByUserId(Long userId) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Account a WHERE a.user.id = :userId", Account.class)
                    .setParameter("userId", userId)
                    .uniqueResultOptional()
                    .orElseThrow(() -> new MovieDbRepositoryOperationException("Account with user ID "
                            + userId + " not found"));
        }
    }

    @Transactional
    public Account create(Account account) {
        getCurrentSession().persist(account);
        return account;
    }

    @Transactional
    public Account update(Account account) {
        Account existingAccount = getCurrentSession().find(Account.class, account.getId());
        if (existingAccount == null) {
            throw new MovieDbRepositoryOperationException("Account with ID " + account.getId() + " not found");
        }
//        account.setUpdatedAt(LocalDateTime.now(ZoneId.of("UTC")));
        getCurrentSession().merge(account);
        return account;
    }

    @Transactional
    public void deleteById(Long id) {
        Account account = getCurrentSession().find(Account.class, id);
        if (account != null) {
            getCurrentSession().remove(account);
        } else {
            throw new MovieDbRepositoryOperationException("Account with ID " + id + " not found for deletion");
        }
    }
}

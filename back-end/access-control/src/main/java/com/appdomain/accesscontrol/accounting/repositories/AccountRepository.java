package com.appdomain.accesscontrol.accounting.repositories;

import com.appdomain.accesscontrol.accounting.domains.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    List<Account> findAllByOrderBySortOrderAsc();

    Long countDistinctByIdIn(final Set<Long> id);
}

package com.appdomain.accesscontrol.accounting.repositories;

import com.appdomain.accesscontrol.accounting.domains.Journal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JournalRepository extends JpaRepository<Journal, Long> {

    List<Journal> findAllByOrderByStartDateDesc();

    Optional<Journal> findFirstByOrderByStartDateDesc();
}

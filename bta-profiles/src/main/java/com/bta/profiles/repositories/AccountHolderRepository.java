package com.bta.profiles.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bta.profiles.entities.AccountHolder;

public interface AccountHolderRepository extends JpaRepository<AccountHolder, Long>{

}

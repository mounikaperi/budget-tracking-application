package com.bta.btatxns.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bta.btatxns.entities.AccountHolder;

public interface AccountHolderRepository extends JpaRepository<AccountHolder, Long>{
	

}

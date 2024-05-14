package com.bta.btastatement.services;


import java.time.LocalDate;

import com.bta.btastatement.exceptions.StatementException;
import com.bta.btastatement.models.Statement;

public interface StatementService {
	
	Statement getStatement(long accountHolderId, LocalDate start, LocalDate end) throws StatementException;

}

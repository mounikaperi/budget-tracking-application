package com.bta.btatxns.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="profiles", url="http://localhost:8001")
public interface ProfilesClient {
	
	@GetMapping("/{accountHolderId}/exists")
	public Boolean checkAccountHolderExists(@PathVariable("accountHolderId") Long accountHolderId);

}

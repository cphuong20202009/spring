package nghia.account.controller;

import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import nghia.account.model.Account;
import nghia.account.repository.AccountRepo;

@RestController
public class AccountController {

	@Autowired
	AccountRepo accountRepo;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AccountController.class);
	
	@RequestMapping(value = "/accounts/model", method = RequestMethod.GET)
	@ResponseStatus(code = HttpStatus.OK)
	@Cacheable("account")
	public Account getModel() {
		Account account = new Account();
		return account;
	}

	
	@RequestMapping(value = "/accounts/{username}", method = RequestMethod.GET)
	@Cacheable(value ="accountCache", key = "#username")
	public ResponseEntity<?> get(@PathVariable String username) {
		
		Optional<Account> optAccount = accountRepo.findByUserName(username);
		if (optAccount.isPresent())
			return new ResponseEntity<Account> (optAccount.get(), HttpStatus.OK);
		StringBuilder errorMessage = new StringBuilder("Not found account with username: ");
		errorMessage.append(username);
		LOGGER.info("Get account not cache");
		return new ResponseEntity<String>(errorMessage.toString(), HttpStatus.NOT_FOUND);
	}
		
	@ResponseStatus(code = HttpStatus.CREATED)
	@RequestMapping(value = "/accounts", method = RequestMethod.POST)
	@CachePut(value ="accountCache", key = "#account.username")
	public ResponseEntity<?> post(@RequestBody Account account) {
		
		Optional<Account> optAccount= accountRepo.findByUserName(account.getUsername());
		if (optAccount.isPresent()){
			StringBuilder errorMessage = new StringBuilder("Account with username: ");
			errorMessage.append(account.getUsername());
			errorMessage.append("alreay exist!");
			return new ResponseEntity<String>(errorMessage.toString(), HttpStatus.FOUND);
		}
		return new ResponseEntity<Account> (optAccount.get(), HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/{username}", method = RequestMethod.DELETE)
	@ResponseStatus(code = HttpStatus.OK)
	@CacheEvict(value ="accountCache", key = "#username")
	public ResponseEntity<?> delete(@PathVariable String username) {
		
		try {
			accountRepo.deleteByUserName(username);
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.ok("Fail");
		}
		return ResponseEntity.ok("Success");
	}
	
	@ResponseStatus(code = HttpStatus.OK)
	@RequestMapping(value = "/accounts", method = RequestMethod.GET)
	public Page<Account> getAllAccount(Pageable pageable) {
		return accountRepo.findAll(pageable);
	}
	
}

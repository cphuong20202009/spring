package nghia.account.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import nghia.account.model.Account;

@RepositoryRestResource(collectionResourceRel = "people", path = "people")
public interface AccountRepo extends PagingAndSortingRepository<Account,UUID> {
	
	
	@Query("SELECT account FROM Account account WHERE account.username = :username")
	Optional<Account> findByUserName (@Param("username")String username);
	
	@Query("DELETE FROM Account account WHERE account.username = :username")
	void deleteByUserName (@Param("username")String username);
	
}

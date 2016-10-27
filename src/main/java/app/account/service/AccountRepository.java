package app.account.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import app.account.entity.Account;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {
	@Query("select a from Account a where a.account=:account")
	Account getByIdentity(@Param("account")String account);
	Account getByAccount(String account);
	List<Account> getByRegion(Long region);
}

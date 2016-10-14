package app.account.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import app.account.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
	@Query("select a from Account a where a.account=:account and a.password=:password")
	Account getByIdentity(@Param("account")String account,@Param("password")String password);
	Account getByAccount(String account);
}

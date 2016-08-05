package app.account.service;

import javax.persistence.EntityManagerFactory;
import javax.servlet.http.HttpServletRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import app.account.entity.Account;
import app.base.DatabaseService;

@Component
public class AccountService extends DatabaseService{

	public AccountService(JdbcTemplate jdbcTemplate, EntityManagerFactory factory) {
		super(jdbcTemplate, factory);
	}

	public Account getAccount(Long id) throws Exception{
		return this.get(Account.class, id);
	}
	
	public String accountInputCheck(Account account) {

		if (account.getAccount() == null || account.getAccount().equals("")) {
			return "accountnull";
		}
		if (account.getPassword() == null || account.getPassword().equals("")) {
			return "passwordnull";
		}

		return "";
	}

	public String checkAccount(Account account) throws Exception {

		String inputRes = accountInputCheck(account);

		if (!inputRes.equals("")) {
			return inputRes;
		}

		Account accountDb = this.get(Account.class,
				"select * from account where account=?",
				new String[] { account.getAccount() });

		if (accountDb != null) {
			return "accountexist";
		}

		return "";
	}
	
	public String checkLogin(Account account) throws Exception {

		String inputRes = accountInputCheck(account);

		if (!inputRes.equals("")) {
			return inputRes;
		}

		Account account_db = this.get(Account.class,
				"select * from account where account=? and password=?",
				new String[] { account.getAccount(), account.getPassword() });

		if (account_db == null) {
			return "accounterror";
		} else {
			account.setName(account_db.getName());
			account.setId(account_db.getId());
		}

		return "";
	}
	
    public Account getLoginAccount(HttpServletRequest request) throws Exception {
    	
    	Account account = (Account) request.getSession().getAttribute(
				"login_account");
    	
    	if (account != null) {
			account = getAccount(account.getId());
		}else{
			account = new Account();
		}
    	
        return account;
    }
}

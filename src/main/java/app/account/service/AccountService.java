package app.account.service;

import javax.persistence.EntityManagerFactory;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import app.account.entity.Account;
import app.base.CookieTool;
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
	
	public void login(Account account,HttpServletRequest request,HttpServletResponse response) throws Exception{
		String checkRes = checkLogin(account);
		if (!checkRes.equals("")) {
			account.setMsg(checkRes);
		}else{
			this.LogAccount(request, response, account);
		}
	}
	
    /**
     * <p>Description:get current login account</p>
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public Account getLoginAccount(HttpServletRequest request,HttpServletResponse response) throws Exception { 	
    	Account account = (Account) request.getSession().getAttribute(
				"login_account");    	
    	if (account != null) {
			account = getAccount(account.getId());
		}else{
			account = CookieTool.getCookieAccount(request);
			if(account!=null){
				login(account,request,response);
			}
		}
        return account;
    }
    
    
    /**
     * <p>Description:record session and cookie</p>
     * @param request
     * @param response
     * @param account
     * @throws Exception
     */
    public void LogAccount(HttpServletRequest request, HttpServletResponse response, Account account) throws Exception {
		Account accountSession = new Account();
		accountSession.setId(account.getId());
		request.getSession().setAttribute("login_account", accountSession);
		CookieTool.logCookie(account, request, response);
	}
}

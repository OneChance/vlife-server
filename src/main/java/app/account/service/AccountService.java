package app.account.service;

import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import app.account.entity.Account;
import app.base.CookieTool;
import app.base.DatabaseService;
import app.speice.entity.Species;
import app.speice.service.SpeiceService;

@Component
public class AccountService extends DatabaseService {

	public AccountService(JdbcTemplate jdbcTemplate, EntityManagerFactory factory) {
		super(jdbcTemplate, factory);
	}

	public Account getAccount(Long id) throws Exception {
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

		Account accountDb = this.get(Account.class, "select * from account where account=?",
				new String[] { account.getAccount() });

		if (accountDb != null) {
			return "accountexist";
		}

		return "";
	}

	public Account checkLogin(Account account) throws Exception {

		String inputRes = accountInputCheck(account);

		if (!inputRes.equals("")) {
			account.setMsg(inputRes);
			return account;
		}

		Account account_db = this.get(Account.class, "select * from account where account=? and password=?",
				new String[] { account.getAccount(), account.getPassword() });

		if (account_db == null) {
			account.setMsg("accounterror");
			return account;
		} else {
			return account_db;
		}
	}

	public Account login(Account account, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Account accountRes = checkLogin(account);
		if (accountRes.getMsg() == null || accountRes.getMsg().equals("")) {
			this.LogAccount(request, response, account);
		}
		return accountRes;
	}

	/**
	 * <p>
	 * Description:get current login account
	 * </p>
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public Account getLoginAccount(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Object accountId = request.getSession().getAttribute("login_account");
		Account account = new Account();
		if (accountId != null) {
			account = getAccount(Long.parseLong(accountId.toString()));
			setAccountInfo(account);
		} else {
			account = CookieTool.getCookieAccount(request);
			if (account != null) {
				account = login(account, request, response);
				setAccountInfo(account);
			}
		}
		return account;
	}

	public void setAccountInfo(Account account) throws Exception {
		Species species = speiceService.getSpeices(account);
		account.setSpecies(species);
	}

	/**
	 * <p>
	 * Description:record session and cookie
	 * </p>
	 * 
	 * @param request
	 * @param response
	 * @param account
	 * @throws Exception
	 */
	public void LogAccount(HttpServletRequest request, HttpServletResponse response, Account account) throws Exception {
		request.getSession().setAttribute("login_account", account.getId());
		CookieTool.logCookie(account, request, response);
	}

	@Resource
	SpeiceService speiceService;
}

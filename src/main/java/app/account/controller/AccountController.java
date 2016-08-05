package app.account.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import app.account.entity.Account;
import app.account.service.AccountService;


@RestController
public class AccountController {
	
	@RequestMapping("/getLoginAccount")
	public Account getLoginAccount(HttpServletRequest request,HttpServletResponse response) throws Exception {
		return accountService.getLoginAccount(request);
	}
    
    @RequestMapping("/login")
	public Account login(@RequestBody Account account, HttpServletRequest request,HttpServletResponse response) throws Exception {

		String checkRes = accountService.checkLogin(account);
		if (!checkRes.equals("")) {
			account.setMsg(checkRes);
		}else{
			LogAccount(request, response, account);
		}

		return account;
	}
    
    public void LogAccount(HttpServletRequest request, HttpServletResponse response, Account account) throws Exception {
		Account accountSession = new Account();
		accountSession.setId(account.getId());
		request.getSession().setAttribute("login_account", accountSession);
	}
    
    @Resource
	AccountService accountService;
}

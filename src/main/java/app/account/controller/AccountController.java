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
		return accountService.getLoginAccount(request,response);
	}
    
    @RequestMapping("/login")
	public Account login(@RequestBody Account account, HttpServletRequest request,HttpServletResponse response) throws Exception {
		accountService.login(account,request,response);
		return account;
	}
    
    @Resource
	AccountService accountService;
}

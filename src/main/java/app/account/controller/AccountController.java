package app.account.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.base.NetMessage;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import app.account.entity.Account;
import app.account.service.AccountService;


@RestController
public class AccountController {

    @RequestMapping("/getLoginAccount")
    public Account getLoginAccount(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return accountService.getLoginAccount(request, response);
    }

    @RequestMapping("/login")
    public Account login(@RequestBody Account account, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return accountService.login(account, request, response);
    }

    @RequestMapping("/loginOut")
    public NetMessage loginOut(HttpServletRequest request, HttpServletResponse response) throws Exception {
        accountService.clearAccount(request, response);
        return new NetMessage();
    }


    @Resource
    AccountService accountService;
}

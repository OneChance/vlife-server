package app.role.controller;

import app.account.entity.Account;
import app.account.service.AccountService;
import app.base.NetMessage;
import app.role.service.RoleService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@RestController
public class RoleController {

    @RequestMapping("/changeProperty")
    public NetMessage changeProperty(@RequestBody Account propAccount, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Account loginAccount = accountService.getLoginAccount(request, response);
        return roleService.changeProp(loginAccount, propAccount);
    }

    @RequestMapping("/toReincarnate")
    public Account toReincarnate(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Account loginAccount = accountService.getLoginAccount(request, response);
        loginAccount.setSoulget(roleService.calSoulGet(loginAccount));
        return loginAccount;
    }

    @RequestMapping("/reincarnate")
    public NetMessage reincarnate(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Account loginAccount = accountService.getLoginAccount(request, response);
        return roleService.reincarnate(loginAccount);
    }

    @Resource
    RoleService roleService;
    @Resource
    AccountService accountService;
}

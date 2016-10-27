package app.account.service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.game.service.GameService;
import org.springframework.stereotype.Component;
import app.account.entity.Account;
import app.base.CookieTool;
import app.speice.entity.Species;
import app.speice.service.SpeiceService;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;

@Component
public class AccountService {

    public Account getById(Long id) throws Exception {
        return accountRepository.getOne(id);
    }

    public void save(Account a) {
        accountRepository.save(a);
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

    public Account checkAccount(Account account) throws Exception {

        String inputRes = accountInputCheck(account);

        if (!inputRes.equals("")) {
            account.setMsg(inputRes);
        }

        Account accountDb = accountRepository.getByAccount(account.getAccount());

        if (accountDb != null) {
            account.setMsg("accountexist");
        }

        return account;
    }

    public Account checkLogin(Account account) throws Exception {

        String inputRes = accountInputCheck(account);

        if (!inputRes.equals("")) {
            account.setMsg(inputRes);
            return account;
        }

        Account account_db = accountRepository.getByIdentity(account.getAccount());

        if (account_db == null) {
            account.setMsg("accounterror");
            return account;
        } else {
            if (!account_db.getPassword().equals(getEncryptedPassword(account_db, account.getPassword()))) {
                account.setMsg("accounterror");
                return account;
            }
            return account_db;
        }
    }

    public Account login(Account account, HttpServletRequest request, HttpServletResponse response) throws Exception {

        Account accountRes = new Account();

        if (account.getEnterType() != null) {
            if (account.getEnterType().equals("login")) {
                accountRes = checkLogin(account);
                if (accountRes.getMsg() == null || accountRes.getMsg().equals("")) {
                    this.LogAccount(request, response, account);
                }
            } else {
                accountRes = checkAccount(account);
                if (accountRes.getMsg() == null || accountRes.getMsg().equals("")) {
                    gameService.initAccount(account);
                    encrypt(account);
                    accountRepository.save(account);
                    this.LogAccount(request, response, account);
                }
            }
        } else {
            accountRes.setMsg("enter-type-error");
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
            account = getById(Long.parseLong(accountId.toString()));
            setAccountInfo(account);
        } else {
            account = CookieTool.getCookieAccount(request);
            if (account != null) {
                account.setEnterType("login");
                account = login(account, request, response);
                setAccountInfo(account);
            }
        }
        return account;
    }

    public void setAccountInfo(Account account) throws Exception {
        Species species = speiceService.getSpeices(account);
        account.setSpecies(species);
        account.setRemainTime(gameService.getRemainTime(account, species));
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

    public void clearAccount(HttpServletRequest request, HttpServletResponse response){
        request.getSession().setAttribute("login_account", null);
        CookieTool.cleanCookies(response, "dbc_");
    }

    public void encrypt(Account account) throws NoSuchAlgorithmException {
        SecureRandom csprng = new SecureRandom();
        byte[] randomBytes = new byte[32];
        csprng.nextBytes(randomBytes);

        String salt = bytes2Hex(randomBytes);
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update((account.getPassword() + salt).getBytes());
        String password = bytes2Hex(md.digest());

        account.setPassword(password);
        account.setSalt(salt);
    }

    public String getEncryptedPassword(Account account, String password) throws NoSuchAlgorithmException {
        String salt = account.getSalt();
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update((password + salt).getBytes());
        return bytes2Hex(md.digest());
    }

    public static String bytes2Hex(byte[] bts) {
        String des = "";
        String tmp = null;
        for (int i = 0; i < bts.length; i++) {
            tmp = (Integer.toHexString(bts[i] & 0xFF));
            if (tmp.length() == 1) {
                des += "0";
            }
            des += tmp;
        }
        return des;
    }

    public List<Account> getByRegion(Long region) {
        return accountRepository.getByRegion(region);
    }

    @Resource
    SpeiceService speiceService;
    @Resource
    GameService gameService;
    @Resource
    private AccountRepository accountRepository;
}

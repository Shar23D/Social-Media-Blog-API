package Service;

import Model.Account;
import DAO.AccountDAO;

public class AccountService {
    private AccountDAO accountDAO;

    public AccountService() {
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    public Account addAccount(Account account) {
        // return null if the username already exists
        if (accountDAO.getAccountByUsername(account.getUsername()) != null) {
            return null;
        }
        // controller class checks not null input and valid password
        return accountDAO.insertAccount(account);
    }

    public Account verifyLogin(Account login) {
        Account account = accountDAO.getAccountByUsername(login.getUsername());
        // check if username exists and password is valid
        if (account != null && account.getPassword().equals(login.getPassword())) {
            return account;
        }
        return null;
    }

    public Account getAccountById(int posted_by_id) {
        return accountDAO.getAccountById(posted_by_id);
    }


}

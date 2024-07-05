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
        if (accountDAO.getAccountbyId(account.getAccount_id())) {
            return null;
        }
        else if ((account.getUsername() != null) && (account.getPassword().length() >= 4)) {
            return accountDAO.insertAccount(account);
        }
    }

    public Account verifyLogin(Account login) {
        Account account = accountDAO.getAccountByUserName(login.getUsername());
        if (account != null && account.getPassword().equals(login.getPassword())) {
        return account;
        }
        else {
            return null;
        }
    }

    public Account getAccountId(int posted_by_id) {
        return accountDAO.getAccountById(posted_by_id);
    }


}

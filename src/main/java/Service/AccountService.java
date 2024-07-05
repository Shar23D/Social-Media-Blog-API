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

    public Account addAccount (Account account) {
        if (accountDAO.getAccountbyId(account.getAccount_id() != null)) {
            return null;
        }
        else if ((account.getUsername() != null) && (account.getPassword().length() >= 4)) {
            return accountDAO.insertAccount(account);
        }
    }
}

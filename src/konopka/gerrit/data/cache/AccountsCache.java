package konopka.gerrit.data.cache;


import konopka.gerrit.data.entities.AccountDto;
import konopka.gerrit.data.IAccountsRepository;

import java.util.*;

/**
 * Created by Martin on 27.6.2015.
 */
public class AccountsCache  {

    private Map<String, AccountDto> cacheByEmail;
    private Map<String, AccountDto> cacheByName;
    private Map<Integer, AccountDto> cacheByGerritId;
    private AccountDto nullAccount;

    public AccountsCache() {
        this.cacheByEmail = new HashMap<>();
        this.cacheByName = new HashMap<>();
        this.cacheByGerritId = new HashMap<>();
    }

    public void restore(List<AccountDto> accounts) {
        accounts.forEach(this::cache);
    }

    public boolean isCached(String email, String name, Integer gerritId) {
        return isCachedByGerritId(gerritId) || isCachedByEmail(email) || isCachedByName(name);
    }

    private boolean isCachedByEmail(String email) {
        return (email != null) && cacheByEmail.containsKey(email);
    }

    private boolean isCachedByGerritId(Integer id) {
        return (id != null) && cacheByGerritId.containsKey(id);
    }

    private boolean isCachedByName(String name) {
        return (name != null) && cacheByName.containsKey(name);
    }

    public AccountDto tryGetCached(String email, String name, Integer gerritId) {
        if (isCachedByGerritId(gerritId)) return cacheByGerritId.get(gerritId);
        if (isCachedByEmail(email)) return cacheByEmail.get(email);
        if (isCachedByName(name)) return cacheByName.get(name);
        return getNullAccount();
    }

    public void cache(AccountDto account) {
        if (account.isNull()) {
            setNullAccount(account);
        }
        else {
            if ((account.accountId != null) && isCachedByGerritId(account.accountId) == false)
                cacheByGerritId.put(account.accountId, account);
            if ((account.email != null) && isCachedByEmail(account.email) == false)
                cacheByEmail.put(account.email, account);
            if ((account.name != null) && isCachedByName(account.name) == false) cacheByName.put(account.name, account);
        }
    }

    public boolean hasNullAccount() {
        return nullAccount != null;
    }

    public void setNullAccount(AccountDto account) {
        nullAccount = account;
    }

    public AccountDto getNullAccount() {
        return nullAccount;
    }
}

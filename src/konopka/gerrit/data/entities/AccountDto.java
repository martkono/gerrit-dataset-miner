package konopka.gerrit.data.entities;

/**
 * Created by Martin on 27.6.2015.
 */
public class AccountDto {

    public AccountDto(String name, String email) {
        this(name, email, null, null);
    }

    public AccountDto(String name, String email, String username, Integer accountId) {
        this.name = name;
        this.email = email;
        this.username = username;
        this.accountId = accountId;
    }


    public AccountDto(int id, String name, String email, String username, Integer accountId) {
        this(name, email, username, accountId);
        this.id = id;
    }

    public int id;
    public String name;
    public String email;
    public String username;
    public Integer accountId;

    public boolean isNull() {
        return this.accountId == null
                && this.email == null
                && this.name == null
                && this.username == null;
    }

    public static AccountDto CreateNullAccount() {
        return new AccountDto(null, null);
    }
}

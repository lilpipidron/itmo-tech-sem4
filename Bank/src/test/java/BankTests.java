import org.junit.jupiter.api.Test;
import ru.itmo.accounts.CreditAccount;
import ru.itmo.accounts.DebitAccount;
import ru.itmo.banks.Bank;
import ru.itmo.banks.CentralBank;
import ru.itmo.banks.DepositAndInterest;
import ru.itmo.clients.Client;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BankTests {
    @Test
    public void OpenDebitAccount_PutMoneyToAccount() {
        CentralBank centralBank = CentralBank.getInstance();
        ArrayList<DepositAndInterest> depositAndInterests = new ArrayList<>();
        depositAndInterests.add(new DepositAndInterest(new BigDecimal(12), 21));
        Bank bank = centralBank.createBank("test", 12, new BigDecimal(12), new BigDecimal(12), depositAndInterests);
        Client client = bank.createClient("123", "123", "123", "123");
        DebitAccount account = bank.createDebitAccount(client.getId(),client);
        account.replenishment(new BigDecimal(123));
        assertEquals(new BigDecimal(123), account.getBalance());
    }

    @Test
    public void OpenDebitAccount_WithdrawMoneyFromAccount() {
        CentralBank centralBank = CentralBank.getInstance();
        ArrayList<DepositAndInterest> depositAndInterests = new ArrayList<>();
        depositAndInterests.add(new DepositAndInterest(new BigDecimal(12), 21));
        Bank bank = centralBank.createBank("test", 12, new BigDecimal(12), new BigDecimal(12), depositAndInterests);
        Client client = bank.createClient("123", "123", "123", "123");
        DebitAccount account = bank.createDebitAccount(client.getId(),client);
        account.replenishment(new BigDecimal(123));
        account.withdraw(new BigDecimal(123));
        assertEquals(new BigDecimal(0), account.getBalance());
    }

    @Test
    public void OpenCreditAccount_WithDrawMoneyFromAccount() {
        CentralBank centralBank = CentralBank.getInstance();
        ArrayList<DepositAndInterest> depositAndInterests = new ArrayList<>();
        depositAndInterests.add(new DepositAndInterest(new BigDecimal(12), 21));
        Bank bank = centralBank.createBank("test", 12, new BigDecimal(12), new BigDecimal(12), depositAndInterests);
        Client client = bank.createClient("123", "123", "123", "123");
        CreditAccount account = bank.createCreditAccount(client.getId(),client);
        account.withdraw(new BigDecimal(24));
        assertEquals(new BigDecimal(-24), account.getBalance());
    }
}

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.kramskoi.accounts.CreditAccount;
import ru.kramskoi.accounts.DebitAccount;
import ru.kramskoi.banks.Bank;
import ru.kramskoi.banks.CentralBank;
import ru.kramskoi.banks.DepositAndInterest;
import ru.kramskoi.clients.Client;
import ru.kramskoi.exceptions.TransactionException;
import ru.kramskoi.transactions.Transaction;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class BankTests {
    private CentralBank centralBank;
    private Bank bank;

    private ArrayList depositAndInterests;

    @BeforeEach
    public void setup() {
        centralBank = CentralBank.getInstance();
        depositAndInterests = new ArrayList<>();
        depositAndInterests.add(new DepositAndInterest(new BigDecimal(12), 21));
        bank = centralBank.createBank("test", 12, new BigDecimal(12), new BigDecimal(12), depositAndInterests);
    }

    @Test
    public void openDebitAccount_putMoneyToAccount_success() throws TransactionException {
        Client client = bank.createClient("123", "123", "123", "123");
        DebitAccount account = bank.createDebitAccount(client.getId(), client);
        bank.replenishmentTransaction(account, new BigDecimal(123));
        assertEquals(new BigDecimal(123), account.getBalance());
    }

    @Test
    public void openDebitAccount_withdrawMoneyFromAccount_success() throws TransactionException {
        Client client = bank.createClient("123", "123", "123", "123");
        DebitAccount account = bank.createDebitAccount(client.getId(), client);
        bank.replenishmentTransaction(account, new BigDecimal(123));
        bank.withdrawTransaction(account, new BigDecimal(123));
        assertEquals(BigDecimal.ZERO, account.getBalance());
    }

    @ParameterizedTest
    @CsvSource({"24"})
    public void openCreditAccount_withdrawMoneyFromAccount_success(BigDecimal amount) throws TransactionException {
        Client client = bank.createClient("123", "123", "123", "123");
        CreditAccount account = bank.createCreditAccount(client.getId(), client);
        bank.withdrawTransaction(account, amount);
        assertEquals(amount.negate(), account.getBalance());
    }

    @Test
    public void openTwoDebitAccount_transferBetweenAccounts_exception() throws TransactionException {
        Client client1 = bank.createClient("1", "123", "123", "123");
        Client client2 = bank.createClient("2", "123", "123", "123");
        DebitAccount account1 = bank.createDebitAccount(client1.getId(), client1);
        DebitAccount account2 = bank.createDebitAccount(client2.getId(), client2);
        bank.replenishmentTransaction(account1, new BigDecimal(100));
        assertThrows(TransactionException.class, () -> {
            bank.transferTransaction(account2, client2, bank.getId(), client1.getId(), account1.getAccountId(), new BigDecimal(100));
        });
    }

    @Test
    public void openTwoDebitAccount_transferBetweenAccounts_success() throws TransactionException {
        Client client1 = bank.createClient("1", "123", "123", "123");
        Client client2 = bank.createClient("2", "123", "123", "123");
        DebitAccount account1 = bank.createDebitAccount(client1.getId(), client1);
        DebitAccount account2 = bank.createDebitAccount(client2.getId(), client2);
        bank.replenishmentTransaction(account2, new BigDecimal(100));
        bank.transferTransaction(account2, client2, bank.getId(), client1.getId(), account1.getAccountId(), new BigDecimal(100));
        assertAll(() -> assertEquals(account1.getBalance(), new BigDecimal(100)),
                () -> assertEquals(account2.getBalance(), BigDecimal.ZERO));
    }

    @Test
    public void openTwoDebitAccount_transferBetweenAccountsAndCancel_success() throws TransactionException {
        Bank bank2 = centralBank.createBank("test5", 12, new BigDecimal(12), new BigDecimal(12), depositAndInterests);
        Client client1 = bank.createClient("1", "123", "123", "123");
        Client client2 = bank2.createClient("2", "123", "123", "123");
        DebitAccount account1 = bank.createDebitAccount(client1.getId(), client1);
        DebitAccount account2 = bank2.createDebitAccount(client2.getId(), client2);
        bank.replenishmentTransaction(account1, new BigDecimal(100));
        Transaction transaction = bank.transferTransaction(account1, client1, bank2.getId(), client2.getId(), account2.getAccountId(), new BigDecimal(100));
        bank.cancelTransaction(account1, transaction.getTransactionId());
        assertAll(() -> assertEquals(account1.getBalance(), new BigDecimal(100)),
                () -> assertEquals(account2.getBalance(), BigDecimal.ZERO));
    }
}
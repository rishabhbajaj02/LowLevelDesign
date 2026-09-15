public class ATMTest {
    public static void main(String[] args) {
        BankService bankService = new BankService();
        Card card = new Card("1234567890123456", 1234);
        UserAccount account = new UserAccount(1, "ACC-1", 1000);
        bankService.registerAccount(account);
        bankService.linkCard(card, account);

        ATMSystem atm = new ATMSystem(bankService, new CashDispenserManager());
        atm.insertCard(card);
        atm.insertPIN(1234);
        atm.selectOperations(OperationType.WITHDRAWAL, 180);

        assert account.getBalance() == 820 : "Withdrawal should reduce balance";
        assert atm.getATMState() instanceof Authenticated : "ATM should remain authenticated";

        atm.selectOperations(OperationType.DEPOSIT, 80);
        assert account.getBalance() == 900 : "Deposit should increase balance";

        atm.selectOperations(OperationType.WITHDRAWAL, 25);
        assert account.getBalance() == 900 : "Unsupported cash amount must not change balance";

        System.out.println("ATMTest passed");
    }
}

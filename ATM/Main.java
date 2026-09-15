public class Main{

    public static void main(String[] args) {
        BankService bankService = new BankService();
        CashDispenserManager cashDispenser = new CashDispenserManager();

        UserAccount account = new UserAccount(1, "ACC-1001", 1000);
        Card card = new Card("5555444433331111", 1234);
        bankService.registerAccount(account);
        bankService.linkCard(card, account);

        ATMSystem atm = new ATMSystem(bankService, cashDispenser);
        atm.insertCard(card);
        atm.insertPIN(1234);
        atm.selectOperations(OperationType.BALANCE_INQUIRY, 0);
        atm.selectOperations(OperationType.DEPOSIT, 250);
        atm.selectOperations(OperationType.WITHDRAWAL, 180);
        atm.selectOperations(OperationType.BALANCE_INQUIRY, 0);
        atm.ejectCard();
    }
}


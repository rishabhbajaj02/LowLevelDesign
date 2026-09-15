public class ATMSystem{
    
    private ATMState atmState;

    private String cardNumber;

    private final BankService bankService;

    private final CashDispenserManager cashDispenser;
    
    public ATMSystem(){
        this(new BankService(), new CashDispenserManager());
    }

    public ATMSystem(BankService bankService, CashDispenserManager cashDispenser){
        if(bankService == null || cashDispenser == null){
            throw new IllegalArgumentException("Bank service and cash dispenser are required.");
        }
        this.bankService = bankService;
        this.cashDispenser = cashDispenser;
        this.atmState = new Idle(); // Assuming NoCard is the initial state of the ATM
    }

    public void setATMState(ATMState atmState){
        this.atmState = atmState;
    }

    public void setCardNumber(String cardNumber){
        this.cardNumber = cardNumber;
    }
    

    public String getCardNumber(){
        return this.cardNumber;
    }

    public Card getCard(){
        return cardNumber == null ? null : bankService.getCard(cardNumber);
    }

    public ATMState getATMState(){
        return this.atmState;
    }

    public void insertCard(Card card){
        atmState.insertCard(this, card);
    }

    public void ejectCard(){
        atmState.ejectCard(this);
    }

    public void insertPIN(int pin){
        atmState.insertPIN(this, pin);
    }

    public void selectOperations(OperationType operationType, int amount){
        atmState.selectOperations(this, operationType, amount);
    }

    public void withdraw(int amount){
        if(!bankService.canWithdraw(cardNumber, amount)){
            bankService.withdraw(cardNumber, amount);
            return;
        }

        if(!cashDispenser.canDispense(amount)){
            System.out.println("Cannot dispense the requested amount.");
            return;
        }

        cashDispenser.dispenseCash(amount);
        bankService.withdraw(cardNumber, amount);
    }

    public boolean validatePin(int pin){
        return cardNumber != null && bankService.validatePin(cardNumber, pin);
    }

    public BankService getBankService(){
        return bankService;
    }
}

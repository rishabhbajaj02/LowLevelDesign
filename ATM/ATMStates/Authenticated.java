public class Authenticated implements ATMState{

    public Authenticated(){

    }


    @Override
    public void insertCard(ATMSystem atmSystem, Card card) {
        System.out.println("Card is already inserted and authenticated");
    }


    @Override
    public void ejectCard(ATMSystem atmSystem) {
        System.out.println("Card ejected.");
        atmSystem.setATMState(new Idle()); // Transition to Idle state after ejecting the card
    }

    @Override
    public void insertPIN(ATMSystem atmSystem, int pin) {
        System.out.println("Already authenticated.");
    }

    @Override
    public void selectOperations(ATMSystem atmSystem, OperationType operationType, int amount) {
        if(operationType == OperationType.BALANCE_INQUIRY){
            System.out.println("Balance inquiry selected.");
            atmSystem.checkBalance();
        } else if(operationType == OperationType.WITHDRAWAL){
            System.out.println("Withdrawal selected with amount: " + amount);
            atmSystem.withdraw(amount);
        } else if(operationType == OperationType.DEPOSIT){
            System.out.println("Deposit selected with amount: " + amount);
            atmSystem.deposit(amount);
        } else{
            System.out.println("Invalid operation selected.");
        }

        System.out.println("Operation completed.");
        ejectCard(atmSystem);
    }

}

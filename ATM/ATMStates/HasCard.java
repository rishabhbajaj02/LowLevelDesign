public class HasCard implements ATMState{

    @Override
    public void insertCard(ATMSystem atmSystem, Card card) {
        System.out.println("Card is already inserted.");
    }

    @Override
    public void ejectCard(ATMSystem atmSystem) {
        System.out.println("Card ejected.");
        atmSystem.setATMState(new Idle()); // Transition to Idle state after ejecting the card
        atmSystem.setCardNumber(null); // Clear the card after ejecting it    
    }

    @Override
    public void insertPIN(ATMSystem atmSystem, int pin) {
        if(atmSystem.getCard() != null && atmSystem.validatePin(pin)){
            System.out.println("PIN entered.");
            atmSystem.setATMState(new Authenticated()); // Transition to Authenticated state after correct PIN
        }else{
            if(atmSystem.getCard() != null && !atmSystem.validatePin(pin)){
                System.out.println("Incorrect PIN.");
            }else{
                System.out.println("No card inserted.");
            }
        }
    }

    @Override
    public void selectOperations(ATMSystem atmSystem, OperationType operationType, int amount) {
        System.out.println("Authenticated card first.");
    }
}
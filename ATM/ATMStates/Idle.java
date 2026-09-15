public class Idle implements ATMState{
    @Override
    public void insertCard(ATMSystem atmSystem, Card card) {

        if(card == null || card.getCardNumber() == null){
            System.out.println("Invalid card.");
            return;
        }

        atmSystem.setATMState(new HasCard());
        atmSystem.setCardNumber(card.getCardNumber());
    }

    @Override
    public void ejectCard(ATMSystem atmSystem) {
        System.out.println("No card to eject");
    }

    @Override
    public void insertPIN(ATMSystem atmSystem, int pin) {
        System.out.println("Insert Card first");
    }

    @Override
    public void selectOperations(ATMSystem atmSystem, OperationType operationType, int amount) {
        System.out.println("Insert Card first");
    }
}
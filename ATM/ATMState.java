public interface ATMState{
    public void insertCard(ATMSystem atmSystem, Card card);
    public void ejectCard(ATMSystem atmSystem);
    public void insertPIN(ATMSystem atmSystem, int pin);
    public void selectOperations(ATMSystem atmSystem, OperationType operationType, int amount);
}
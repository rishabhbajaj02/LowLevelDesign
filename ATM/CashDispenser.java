public interface CashDispenser{
    public void dispense(int amount);
    public boolean canDispense(int amount);
    public void setNextChain(CashDispenser nextDispenser);
}
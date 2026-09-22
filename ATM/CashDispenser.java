public interface CashDispenser{
    public boolean dispense(int amount);
    public boolean canDispense(int amount);
    public void setNextChain(CashDispenser nextDispenser);
}
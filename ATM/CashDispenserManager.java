public class CashDispenserManager{

    private CashDispenser firstDispenser;

    public CashDispenserManager(){
        CashDispenser d100 = new NoteDispenser(100, 10);
        CashDispenser d50 = new NoteDispenser(50, 20);
        CashDispenser d20 = new NoteDispenser(20, 40);
        CashDispenser d10 = new NoteDispenser(10, 50);

        d100.setNextChain(d50);
        d50.setNextChain(d20);
        d20.setNextChain(d10);

        this.firstDispenser = d100;
    }

    public void dispenseCash(int amount){
        if(firstDispenser != null && firstDispenser.canDispense(amount)){
            System.out.println("Transaction approved for amount: " + amount);
            firstDispenser.dispense(amount);
        }else{
            System.out.println("Cannot dispense the requested amount: " + amount);
        }
    }

    public boolean canDispense(int amount){
        return amount > 0 && firstDispenser != null && firstDispenser.canDispense(amount);
    }


}

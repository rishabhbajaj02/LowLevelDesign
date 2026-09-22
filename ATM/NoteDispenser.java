public class NoteDispenser implements CashDispenser{

    private int noteValue;

    private int quantity;

    private CashDispenser nextDispenser;

    public NoteDispenser(int noteValue, int quantity){
        this.noteValue = noteValue;
        this.quantity = quantity;
    }

    @Override
    public void setNextChain(CashDispenser nextDispenser){
        this.nextDispenser = nextDispenser;
    }

    @Override
    public synchronized void dispense(int amount){
        if(canDispense(amount)){
            int notesNeeded = amount / noteValue;
            int notesToUse = Math.min(notesNeeded, quantity);
            int remainder = amount - (notesToUse * noteValue);

            this.quantity -= notesToUse;

            if(remainder > 0 && this.nextDispenser != null){
                this.nextDispenser.dispense(remainder);
            }

            return true;
        }

        return false;
    }

    @Override
    public synchronized boolean canDispense(int amount){
        int notesNeeded = amount / noteValue;

        int notesToUse = Math.min(notesNeeded, quantity);

        int remainder = amount - (notesToUse * noteValue);

        if(remainder == 0){
            return true;
        }

        if(this.nextDispenser == null){
            return false;
        }

        return this.nextDispenser.canDispense(remainder);
    }

}
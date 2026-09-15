import java.util.HashMap;
import java.util.Map;

public class UserAccount{
    private int id;

    private Map<String, Card> cards = new HashMap<>();

    private double balance;

    private String accountNumber;

    public UserAccount(int id, String accountNumber, double balance){
        this.id = id;
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public void addCard(Card card){
        this.cards.put(card.getCardNumber(), card);
    }

    public int getId(){
        return this.id;
    }

    public String getAccountNumber(){
        return this.accountNumber;
    }

    public double getBalance(){
        return this.balance;
    }

    public void setBalance(double balance){
        this.balance = balance;
    }

    public Card getCard(String cardNumber){
        return this.cards.get(cardNumber);
    }

}
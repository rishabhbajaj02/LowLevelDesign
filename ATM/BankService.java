import java.util.Map;
import java.util.HashMap;

public class BankService{
    private Map<String, UserAccount> userAccounts = new HashMap<>();
    
    // Mapping of card numbers to user accounts
    private Map<String, UserAccount> cardsToAccount = new HashMap<>();


    public void registerAccount(UserAccount userAccount){
        userAccounts.put(userAccount.getAccountNumber(), userAccount);
       
    }

    public void linkCard(Card card, UserAccount userAccount){
        cardsToAccount.put(card.getCardNumber(), userAccount);
        userAccount.addCard(card);
    }

    public boolean validatePin(String cardNumber, int pin){
        if(cardsToAccount.containsKey(cardNumber)){
            Card card = cardsToAccount.get(cardNumber).getCard(cardNumber);
            return card.getPin() == pin;
        }
        return false;
    }

    public void checkBalance(String cardNumber){
        if(cardsToAccount.containsKey(cardNumber)){
            UserAccount userAccount = cardsToAccount.get(cardNumber);
            System.out.println("Balance: " + userAccount.getBalance());
        } else{
            System.out.println("Card not linked to any account.");
        }
    }

    public void deposit(String cardNumber, int amount){
        if(amount <= 0){
            System.out.println("Deposit amount must be positive.");
            return;
        }
        if(cardsToAccount.containsKey(cardNumber)){
            UserAccount userAccount = cardsToAccount.get(cardNumber);
            userAccount.setBalance(userAccount.getBalance() + amount);
            System.out.println("Deposit successful. New balance: " + userAccount.getBalance());
        } else{
            System.out.println("Card not linked to any account.");
        }
    }

    public boolean canWithdraw(String cardNumber, int amount){
        UserAccount userAccount = cardsToAccount.get(cardNumber);
        return amount > 0 && userAccount != null && userAccount.getBalance() >= amount;
    }

    public boolean withdraw(String cardNumber, int amount){
        if(!canWithdraw(cardNumber, amount)){
            if(amount <= 0){
                System.out.println("Withdrawal amount must be positive.");
            } else if(!cardsToAccount.containsKey(cardNumber)){
                System.out.println("Card not linked to any account.");
            } else {
                System.out.println("Insufficient balance.");
            }
            return false;
        }

        UserAccount userAccount = cardsToAccount.get(cardNumber);
        userAccount.setBalance(userAccount.getBalance() - amount);
        System.out.println("Withdrawal successful. New balance: " + userAccount.getBalance());
        return true;
    }

    public UserAccount getUserAccount(String cardNumber){
        return cardsToAccount.get(cardNumber);
    }

    public Card getCard(String cardNumber){
        UserAccount account = cardsToAccount.get(cardNumber);
        return account == null ? null : account.getCard(cardNumber);
    }
}

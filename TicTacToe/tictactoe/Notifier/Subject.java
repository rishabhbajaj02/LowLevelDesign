package tictactoe.Notifier;

import java.util.ArrayList;
import java.util.List;
import tictactoe.Game;

public abstract class Subject{

    private List<Observer> observers = new ArrayList<>();

    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public void notifyObservers(Game game) {
        for (Observer observer : observers) {
            observer.update(game);
        }
    }
}

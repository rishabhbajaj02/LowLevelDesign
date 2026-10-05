import java.util.Map;
import java.util.Optional;

public abstract class SelectionStrategy {
    public abstract Optional<Elevator> getElevator(Map<Integer, Elevator> elevators, Command command);

    protected boolean isEligible(Elevator elevator, int currentFloor, Direction direction) {
        if(direction == Direction.UP) {
            return elevator.floor.get() <= currentFloor;
        } else if(direction == Direction.DOWN) {
            return elevator.floor.get() >= currentFloor;
        }
        return false;
    }
}
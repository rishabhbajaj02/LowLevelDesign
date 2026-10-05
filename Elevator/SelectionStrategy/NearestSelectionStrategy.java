import java.util.Map;
import java.util.Optional;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
public class NearestSelectionStrategy extends SelectionStrategy {
    
    @Override
    public Optional<Elevator> getElevator(Map<Integer, Elevator> elevators, Command command){

        Source source  = command.getSource();

        if(source == Source.EXTERNAL){
            List<Elevator> elevatorList = new ArrayList<>();

            for (Elevator elevator : elevators.values()) {
                if(isEligible(elevator, command.getFloor(), command.getDirection())){
                    elevatorList.add(elevator);
                }
            }

            Optional<Elevator> nearestElevator = elevatorList.stream()
        .min(Comparator.comparingInt(e -> Math.abs(e.floor.get() - command.getFloor())));

            return nearestElevator;
        }

        return Optional.empty();
    }
}
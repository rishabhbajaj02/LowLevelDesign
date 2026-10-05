import java.util.Map;

public class Main{
    public static void main(String[] args){
        Elevator elevator1 = new Elevator(1, 2); // Elevator with ID 1 starting at floor 0
        Elevator elevator2 = new Elevator(2, 2); // Elevator with ID 2 starting at floor 0 

        ElevatorSystem elevatorSystem = ElevatorSystem.getInstance(
            Map.of(
                1, elevator1,
                2, elevator2
            ),
            new NearestSelectionStrategy()
        );

        elevatorSystem.start();

        elevatorSystem.requestElevator(new Command(Source.EXTERNAL, Direction.UP, 5)); 
        elevatorSystem.selectFloor(1, 3); 
        elevatorSystem.requestElevator(new Command(Source.EXTERNAL, Direction.UP, 2)); 
        elevatorSystem.selectFloor(2, 0); 
        elevatorSystem.requestElevator(new Command(Source.EXTERNAL, Direction.UP, 3)); 
        elevatorSystem.selectFloor(2, 0); 
        elevatorSystem.requestElevator(new Command(Source.EXTERNAL, Direction.UP, 4)); 
        elevatorSystem.selectFloor(1, 0); 
        elevatorSystem.selectFloor(2, 0); 
        elevatorSystem.requestElevator(new Command(Source.EXTERNAL, Direction.UP, 4)); 
        elevatorSystem.requestElevator(new Command(Source.EXTERNAL, Direction.UP, 1)); 
        

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // internally shutdown will wait for all elevators to move to Idle State before actually shutting down
        elevatorSystem.shutdown();  
    }

}
public class IdleState implements ElevatorState {
    @Override
    public void move(Elevator elevator) {
        try {
            Thread.sleep(1000); // Sleep for 1 second to simulate idle state
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Check if there are any commands to move the elevator
        if (elevator.upCommands.size() > 0) {
            elevator.changeState(new MovingUpState());
            return; // Exit after changing state to MovingUpState
        } else if (elevator.downCommands.size() > 0) {
            elevator.changeState(new MovingDownState());
            return;
        } else {
            // Idle does not move elevator
            System.out.println("Elevator is idle.");

        }
    }

}
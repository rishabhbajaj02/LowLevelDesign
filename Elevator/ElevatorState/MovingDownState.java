public class MovingDownState implements ElevatorState {

    @Override
    public void move(Elevator elevator) {
        // Moving down state moves elevator down
        // sets the next state based on command received

        Integer targetFloor = elevator.downCommands.pollLast();

        if (targetFloor == null) {
            elevator.changeState(new IdleState());
            return;
        }

        while (elevator.floor.get() > targetFloor) {
            try {
                Thread.sleep(1000); // Sleep for 1 second to simulate moving down
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            elevator.floor.getAndDecrement();
            int currentFloor = elevator.floor.get();

            System.out.println("Elevator currently at floor " + elevator.floor.get());

            if (elevator.downCommands.remove(currentFloor)) {
                System.out.println("Elevator stopped early at requested floor " + elevator.floor.get());
                simulateDoorsOpenClose();
            }

        }

        System.out.println("Elevator reached primary requested floor " + elevator.floor.get());
        simulateDoorsOpenClose();

        // If there are no more down commands, switch to idle state
        if (elevator.downCommands.isEmpty()) {
            elevator.changeState(new IdleState());
        }

    }

    private void simulateDoorsOpenClose() {
        try {
            Thread.sleep(3000); // Simulate doors open/close
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.atomic.AtomicInteger;

public class Elevator implements Runnable {

    private int id;

    public AtomicInteger floor;

    private volatile ElevatorState elevatorState;

    public ConcurrentSkipListSet<Integer> upCommands = new ConcurrentSkipListSet<>();
    public ConcurrentSkipListSet<Integer> downCommands = new ConcurrentSkipListSet<>();

    private volatile boolean isRunning = true; 

    public Elevator(int id, int initialFloor) {
        this.id = id;
        this.floor = new AtomicInteger(initialFloor);

        this.elevatorState = new IdleState();
    }

    public synchronized void addCommand(Command command){

        int targetFloor = command.getFloor();

        if(targetFloor < 0){
            throw new IllegalArgumentException("Invalid floor: " + targetFloor);
        }

        if(targetFloor == floor.get()) {
            System.out.println("Elevator is already at floor " + targetFloor + ".");
            return;
        }

        Direction direction = command.getDirection();
        if(direction == Direction.UP) {
            if(upCommands.contains(command.getFloor())) {
                System.out.println("Command for floor " + command.getFloor() + " already exists in upCommands.");
                return;
            }

            upCommands.add(command.getFloor());
        } else if(direction == Direction.DOWN) {
            if(downCommands.contains(command.getFloor())) {
                System.out.println("Command for floor " + command.getFloor() + " already exists in downCommands.");
                return;
            }
            downCommands.add(command.getFloor());
        }
    }

    public int getId() {
        return id;
    }

    public void changeState(ElevatorState newState) {
        this.elevatorState = newState;
    }

    public boolean isIdle() {
        return elevatorState instanceof IdleState
            && upCommands.isEmpty()
            && downCommands.isEmpty();
    }

    public void move() {
        while (isRunning && !Thread.currentThread().isInterrupted()) {
            elevatorState.move(this);
        }
    }

    public void stop() {
        isRunning = false;
    }


    @Override
    public void run() {
        // Start moving the elevator
        move();
    }
}
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ElevatorSystem {

    private SelectionStrategy selectionStrategy;

    private HashMap<Integer, Elevator> elevators = new HashMap<>();

    private ExecutorService executorService;

    private static ElevatorSystem instance;
    private boolean started;
    private boolean shutdownRequested;

    private ElevatorSystem(Map<Integer, Elevator> elevators, SelectionStrategy selectionStrategy) {
        this.elevators.putAll(elevators);
        this.selectionStrategy = selectionStrategy;
        this.executorService = Executors.newFixedThreadPool(elevators.size());
    }

    public static synchronized ElevatorSystem getInstance(Map<Integer, Elevator> elevators, SelectionStrategy selectionStrategy) {
        if (instance == null) {
            instance = new ElevatorSystem(elevators, selectionStrategy);
        }
        return instance;
    }

    public synchronized void requestElevator(Command command){
        if (shutdownRequested) {
            throw new IllegalStateException("Elevator system is shutting down.");
        }
        // gets nearest and eligible elevator based on selection strategy
        var elevator = selectionStrategy.getElevator(elevators, command);

        if(elevator.isPresent()){
            elevator.get().addCommand(command);
        } else {
            System.out.println("No eligible elevator found for the requested floor " + command.getFloor() + ".");
        }
    }

    public synchronized void selectFloor(int elevatorId, int targetFloor){
        if (shutdownRequested) {
            throw new IllegalStateException("Elevator system is shutting down.");
        }
        Elevator elevator = elevators.get(elevatorId);
        if(elevator != null){
            if(targetFloor > elevator.floor.get()) {
                elevator.addCommand(new Command(Source.INTERNAL, Direction.UP, targetFloor));
            } else if(targetFloor < elevator.floor.get()) {
                elevator.addCommand(new Command(Source.INTERNAL, Direction.DOWN, targetFloor));
            }
        } else {
            System.out.println("Elevator with ID " + elevatorId + " not found.");
        }
    }

    public synchronized void start(){
        if (shutdownRequested) {
            throw new IllegalStateException("Elevator system is shutting down.");
        }
        started = true;
        for (Elevator elevator : elevators.values()) {
            executorService.submit(elevator);
        }
    }

    public void shutdown() {
        boolean waitForElevators;
        synchronized (this) {
            if (shutdownRequested) {
                return;
            }
            shutdownRequested = true;
            waitForElevators = started;
        }

        boolean interrupted = false;
        while (waitForElevators && elevators.values().stream().anyMatch(elevator -> !elevator.isIdle())) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                interrupted = true;
            }
        }

        for(Elevator elevator : elevators.values()) {
            elevator.stop();
        }

        executorService.shutdown();
        if (interrupted) {
            Thread.currentThread().interrupt();
        }
    }
}
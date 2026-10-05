User Flow

1. User will enter a command
    a. From EXTERNAL Source, theres option to requestElevator(Direction direction) # direction indicates the direction user wants to go, requestion will fetch based on Elevator Strategy and based on condition 
        - if elevator below user and user wants to go UP -> elevator is eligible
        - if elevator above user and user wants to go DOWN, -> elevator is eligible
        - else elevator is not eligible for consideration
    b. FROM INTERNAL, there's option to selectFloor(int floor) # which floor to go to
2. Elevator system will fetch the elevator based on strategy and add the command to that elevator
3. Elevator wil move floor by floor, not strating to destination floor (simulate delay between each floor floor), that allows for commands to come inb
3etween the elevator is moving. if up command, add to upCommands else downCommands
4. if in IdleState, process the command that was received either up or down
5. if MovingUpState, process all upCommands first then proceed with down commands
6. similarly, if MovingDown, process down commands first then proceed with upCommands
7.  Once all commands are processed, move to Idle State

Edge Cases

1. Adding multiple commands for the same floor, if command exists for the floor don't add it to the list
2. if current floor is same as target floor
3. if target floor is out of bounds

Thread consideration
1. Each elevator will run on its own thread, allowing for parallel execution for each elevator
2. Elevator will continue running until shutdows (isRunning = true)
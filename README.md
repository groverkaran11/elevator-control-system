# elevator-control-system

## Build Instructions
The project is in the form of a maven project. To build, navigate to the top project directory and type mvn clean package. This will create a jar file in the target folder.

## Design
'ElevatorControlSystem.java' is the Interface class of the system and it offers the following APIs:
```Java
	/*
	 * To find the status of the elevator - elevatorId, current floor, next floor, direction
	 */
	public ElevatorState status(int elevatorId);
	
	/*
	 * Pushing the destination floor button in the elevator
	 */
	public void update(int elevatorId, int destination);
	
	/*
	 * Requesting for an elevator for pickup towards a direction
	 */	
	public void pickup(int floor, Direction direction);
	
	/*
	 * Unit operation of Elevator system
	 */
	public void step();
```

***ElevatorImpl.java*** implements ***Elevator.java*** which models a single elevator through the following APIs:

1. ``` public void addDestFloor(int floorId, int turnBackFromFloor); ```



*ElevatorImpl.java* the following attributes:

1. elevatorId - the unique identifier for the elevator
2. currentFloor - current floor the elevator is positioned at
3. nextFloor - next floor to stop at
4. direction - direction of the elevator
5. upDestinationQueue - queue of floors to stop at in the UP direction
6. downDestinationQueue - queue of floors to stop at in the DOWN direction

For modelling the working of elevator, it offers

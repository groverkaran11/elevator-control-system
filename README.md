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

'ElevatorImpl.java' models a single elevator and it maintains the following attributes:
1. elevatorId
2. currentFloor

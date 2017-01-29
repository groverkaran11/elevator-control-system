# elevator-control-system

## Build Instructions
The project is in the form of a maven project. To build, navigate to the top project directory and type 
```mvn clean package```
This will create a jar file in the target folder.

## Simulation
``` mvn exec:java -Dexec.mainClass="com.meso.ecs.ElevatorControlSystemSimulator" ```

## Design
'ElevatorControlSystem.java' is the Interface class of the system and it offers the following APIs:
```java
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

***ElevatorImpl.java*** implements ***Elevator.java*** which models the working of a single elevator through the following APIs:

1. ``` public void addDestFloor(int floorId, int turnBackFromFloor); ```

   Adds a floor to the destination queue maintained by the elevator
2. ``` public void moveOneStep(); ```

   Moves the elevator by one floor in a given direction and checks if has reached a destination floor. It changes the direction of the elevator if it has finished the destination queue in one/both direction. 

*ElevatorImpl.java* contains the following attributes:

1. elevatorId - the unique identifier for the elevator
2. currentFloor - current floor the elevator is positioned at
3. nextFloor - next floor to stop at
4. direction - direction of the elevator
5. upDestinationQueue - increasingly sorted set of floors to stop at in the UP direction
6. downDestinationQueue - decreasingly sorted set of floors to stop at in the DOWN direction

## Algorithm to make stops
* Each elevator maintains two sorted set of floors it has to stop at, one for each direction, so that drops can be made in the order of floors the elevator will cross through. The destination floors in the TOP direction are sorted in increasing order. Whereas, the destination floors in the DOWN direction are sorted in decreasing order.
* The elevator will continue to move in a given direction till the time the sorted queue for that direction is empty. Once the queue for direction is empty, it'll check if there are stops to be made in the other direction. If yes, the direction will be reversed. Otherwise, the elevator will stand still at the last destination floor. 
* If a floor is pressed in an elevator which is not in the direction of travel of the elevator, that floor will be added in the destination queue of the opposite direction.

## Scheduling Pickup Requests
Algorithm to find and schedule the best elevator to the requested floor follows the given order: 

1. Pick a standing elevator present at the requested floor
2. Pick the closest elevator moving towards the requested floor and headed in the requested direction
3. Pick a standing elevator present at some other floor
4. If no elevator is found in the above 3 categories, add the request to FCFS request queue. At every time step, for each request in the requet queue (in the FCFS order), check if any elevator can be scheduled according to steps 1-3 above.

## Assumption
The passengers get off/on the elevator as soon as the elevator reaches the destination floor.

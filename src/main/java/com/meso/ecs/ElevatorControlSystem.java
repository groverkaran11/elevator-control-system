package com.meso.ecs;

import com.meso.ecs.enums.Direction;

public interface ElevatorControlSystem {
	
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
}

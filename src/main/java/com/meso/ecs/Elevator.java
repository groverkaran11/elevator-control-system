package com.meso.ecs;

import java.util.TreeSet;

import com.meso.ecs.enums.Direction;

public interface Elevator {
	/*
	 * Get elevatorId
	 */
	public int getElevatorId();
	
	/*
	 * Get direction of travel of the elevator
	 */
	public Direction getDirection();
	
	/*
	 * Set the direction of travel of the elevator
	 */
	public void setDirection(Direction direction);
	
	/*
	 * Returns the floor number at which the elevator has to change direction for a pickup
	 */
	public int getFloorToTurnBackFrom();
	
	/*
	 * Get the floor at which the elevator is currently located
	 */
	public int getCurrentFloor();
	
	/*
	 * Get the next floor at which the elevator will stop
	 */
	public int getNextDestFloor();
	
	/*
	 * Add a floor onto the path of the elevator
	 */
	public void addDestFloor(int floorId, int turnBackFromFloor);
	
	/*
	 * Move the elevator one floor
	 */
	public void moveOneStep();
	
    /*
     * To find the status of the elevator - elevatorId, current floor, next floor, direction
     */
	public ElevatorState getStatus();
	
	/*
	 * Returns the destination queue in UP direction
	 */
	public TreeSet<Integer> getUpDestinationQueue();
	
	/*
	 * Returns the destination queue in DOWN direction
	 */
	public TreeSet<Integer> getDownDestinationQueue();
	
}

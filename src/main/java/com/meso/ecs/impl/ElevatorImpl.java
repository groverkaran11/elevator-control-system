package com.meso.ecs.impl;

import java.util.Collections;
import java.util.TreeSet;
import com.meso.ecs.Elevator;
import com.meso.ecs.ElevatorState;
import com.meso.ecs.enums.Direction;

public class ElevatorImpl implements Elevator {
	
	private final int elevatorId;
	private int currentFloor;
	private Direction direction = Direction.STILL;
	private int turnBackFromFloor;
	
	public TreeSet<Integer> upDestinationQueue = new TreeSet<Integer>();
	public TreeSet<Integer> downDestinationQueue = new TreeSet<Integer>(Collections.reverseOrder());
	
	public ElevatorImpl(int elevatorId) {
		this.elevatorId = elevatorId;
		this.currentFloor = 1;
	}
	
	@Override
	public int getElevatorId() {
		return elevatorId;
	}
	
	@Override
	public Direction getDirection() {
		return direction;
	}
	

	@Override
	public void setDirection(Direction direction) {
		this.direction = direction;
	}
	
	@Override
	public int getCurrentFloor() {
		return currentFloor;
	}

	@Override
	public int getNextDestFloor() {
		if(direction == Direction.UP) {
			return upDestinationQueue.first();
		}
		else if(direction == Direction.DOWN) {
			return downDestinationQueue.first();
		}
		else
			return -1;
	}

	@Override
	public void addDestFloor(int floorId, int turnBackFromFloor) {
		
		this.turnBackFromFloor = turnBackFromFloor;
		
		if(floorId == currentFloor) {
			System.out.println("Elevator "+ elevatorId +": Destination is same as current floor. Opening doors");
			return;
		}
		
		if(direction == Direction.UP) {
			if(floorId > currentFloor)
				upDestinationQueue.add(floorId);
			else
				downDestinationQueue.add(floorId);
		}
		else if(direction == Direction.DOWN) {
			if(floorId < currentFloor)
				downDestinationQueue.add(floorId);
			else
				upDestinationQueue.add(floorId);
		}
		else {
			if(floorId > currentFloor) {
				direction = Direction.UP;
				upDestinationQueue.add(floorId);
			}
			else {
				direction = Direction.DOWN;
				downDestinationQueue.add(floorId);
			}
		}
	}

	@Override
	public void moveOneStep() {
		
		if(direction == Direction.UP) {
			/*
			 * This can happen when an elevator is present on the requested floor to go UP
			 * ,doors open but no button is pressed or a DOWN floor is pressed
			 */
			if (upDestinationQueue.isEmpty()) {
				if(downDestinationQueue.isEmpty()) {
					direction = Direction.STILL;
				}
				else {
					direction = Direction.DOWN;
					currentFloor --;
				}
			}
			
			/*
			 *  If elevator has reached next destination floor in UP direction, check if there are more floors 
			 *  to be covered in the UP direction, If yes, move it to one floor UP. Else, check if elevator has been 
			 *  scheduled to go floors in DOWN direction. If yes, change direction to DOWN and move one step. 
			 *  Else, set elevator to STILL
			 */
			else if(currentFloor == getNextDestFloor()) {
				
				if(turnBackFromFloor == currentFloor)
					turnBackFromFloor = -1;
				
				upDestinationQueue.pollFirst();
				if(upDestinationQueue.isEmpty()) {
					if(downDestinationQueue.isEmpty()) {
						direction = Direction.STILL;
					}
					else {
						direction = Direction.DOWN;
						currentFloor --;
					}
				}
				else {
					currentFloor ++;
				}
			}
			
			// Next destination floor hasn't been reached, continue moving
			else {
				currentFloor ++;
			}
		}
		
		else if(direction == Direction.DOWN) {
			
			/*
			 * This can happen when an elevator is present on the requested floor to go DOWN
			 * ,doors open but no button is pressed or a UP floor is pressed
			 */
			if (downDestinationQueue.isEmpty()) {
				if(upDestinationQueue.isEmpty()) {
					direction = Direction.STILL;
				}
				else {
					direction = Direction.UP;
					currentFloor ++;
				}
			}
			
			/*
			 *  If elevator has reached next destination floor in DOWN direction, check if there are more floors 
			 *  to be covered in the DOWN direction, If yes, move it one floor DOWN. Else, check if elevator has been 
			 *  scheduled to go floors in UP direction. If yes, change direction to UP and move one step. 
			 *  Else, set elevator to STILL
			 */
			else if(currentFloor == getNextDestFloor()) {
				
				if(turnBackFromFloor == currentFloor)
					turnBackFromFloor = -1;
				
				downDestinationQueue.pollFirst();
				if(downDestinationQueue.isEmpty()) {
					if(upDestinationQueue.isEmpty()) {
						direction = Direction.STILL;
					}
					else {
						direction = Direction.UP;
						currentFloor ++;
					}
				}
				else {
					currentFloor --;
				}
			}
			
			// Next destination floor hasn't been reached, continue moving			
			else {
				currentFloor --;
			}			
		}
	}

	@Override
	public ElevatorState getStatus() {
		return new ElevatorState(elevatorId, currentFloor, getNextDestFloor(), direction);
	}

	@Override
	public int getFloorToTurnBackFrom() {
		return turnBackFromFloor;
	}

	@Override
	public TreeSet<Integer> getUpDestinationQueue() {
		return upDestinationQueue;
	}

	@Override
	public TreeSet<Integer> getDownDestinationQueue() {
		return downDestinationQueue;
	}

}

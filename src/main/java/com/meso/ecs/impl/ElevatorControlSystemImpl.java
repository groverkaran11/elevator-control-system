package com.meso.ecs.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.meso.ecs.Elevator;
import com.meso.ecs.ElevatorControlSystem;
import com.meso.ecs.ElevatorRequest;
import com.meso.ecs.ElevatorState;
import com.meso.ecs.enums.Direction;

public class ElevatorControlSystemImpl implements ElevatorControlSystem {
	
	public List<Elevator> elevatorList = new ArrayList<Elevator>();
	public List<ElevatorRequest> requests = new ArrayList<ElevatorRequest>();
	
	public ElevatorControlSystemImpl(int num_elevators) {
		for(int i=0 ; i <num_elevators; i++) {
			elevatorList.add(new ElevatorImpl(i));
		}
	}
	
	@Override
	public ElevatorState status(int elevatorId) {
		return elevatorList.get(elevatorId).getStatus();
	}

	@Override
	public void update(int elevatorId, int destination) {
		System.out.println("Elevator "+ elevatorId + " : Pressing floor " + destination);
		elevatorList.get(elevatorId).addDestFloor(destination,-1);
	}
	
	/*
	 * Tries to allocate an elevator to a request. 
	 * If no elevator is free, the pickup request is added to the request queue.
	 */
	@Override
	public void pickup(int floor, Direction direction) {
		
		System.out.println("Pick-up request from floor: "+ floor+" in Direction: "+direction);

		if(!findAndAllocateBestElevator(floor, direction)) {
			requests.add(new ElevatorRequest(floor, direction));
		}
	}
	
	/*
	 * SCHEDULING ALGORITHM 
	 * Algorithm to find and schedule the best elevator to the requested floor follows the given order: 
	 * 1. Pick a standing elevator present at the requested floor
	 * 2. Pick the closest elevator moving towards the requested floor and headed in the requested direction
	 * 3. Pick a standing elevator present at some other floor
	 */
	private boolean findAndAllocateBestElevator(int floor, Direction direction) {
		
		Elevator stillElevatorOnReqFloor = null;
		Elevator closestElevatorMovingTowardsReqFloor = null;
		Elevator stillElevatorOnAnotherFloor = null;
		int pickupFloorToTurnBackFrom = -1;

		for(Elevator elevator : elevatorList) {
			
			if(elevator.getCurrentFloor() == floor && elevator.getDirection() == Direction.STILL) {
				stillElevatorOnReqFloor = elevator;
				break;
			}
			
			else if (elevator.getDirection() == Direction.UP && direction == Direction.UP && elevator.getCurrentFloor()<=floor 
					&& elevator.getFloorToTurnBackFrom()==-1) {
				
				if(closestElevatorMovingTowardsReqFloor == null) {
					closestElevatorMovingTowardsReqFloor = elevator;
				}
				
				else if((floor - elevator.getCurrentFloor()) < (floor - closestElevatorMovingTowardsReqFloor.getCurrentFloor())) {
					closestElevatorMovingTowardsReqFloor = elevator;
				}
			}
			
			else if (elevator.getDirection() == Direction.DOWN && direction == Direction.DOWN && elevator.getCurrentFloor()>=floor
					&& elevator.getFloorToTurnBackFrom()==-1) {
				
				if(closestElevatorMovingTowardsReqFloor == null) {
					closestElevatorMovingTowardsReqFloor = elevator;
				}
				
				else if((elevator.getCurrentFloor()-floor) < (closestElevatorMovingTowardsReqFloor.getCurrentFloor()-floor)) {
					closestElevatorMovingTowardsReqFloor = elevator;
				}
			}
			
			else if(elevator.getDirection() == Direction.STILL) {
				if(stillElevatorOnAnotherFloor == null) {
					stillElevatorOnAnotherFloor = elevator;
				}
				
				else if(Math.abs(floor - elevator.getCurrentFloor()) < Math.abs(floor - stillElevatorOnAnotherFloor.getCurrentFloor())) {
					stillElevatorOnAnotherFloor = elevator;
				}
			}
		}
		
		
		/*
		 * If a STILL elevator is found at the same floor, open its door and assume that the passenger enters destination
		 * (which calls update function) by the next step/tick. Otherwise, elevator will become idle again
		 */
		if(stillElevatorOnReqFloor!=null) {
			System.out.println("Elevator "+stillElevatorOnReqFloor.getElevatorId()+" present on requested floor. Opening doors");
			stillElevatorOnReqFloor.setDirection(direction);
			return true;
		}
		
		/*
		 * Else, if closest elevator moving towards the requested floor 
		 * and in the same requested direction is found, add the requested floor as a destination of the found elevator
		 */
		if(closestElevatorMovingTowardsReqFloor != null) {
			closestElevatorMovingTowardsReqFloor.addDestFloor(floor,-1);
			System.out.println("Elevator "+closestElevatorMovingTowardsReqFloor.getElevatorId()+" will stop at requested floor on its way to " 
								+closestElevatorMovingTowardsReqFloor.getNextDestFloor());
			return true;
		}
		
		/*
		 * Else, if still elevator at another floor is found,
		 * add the requested floor as the destination of the found elevator
		 */
		if(stillElevatorOnAnotherFloor != null) {
			
			if((floor>stillElevatorOnAnotherFloor.getCurrentFloor() && direction==Direction.DOWN) || 
					(floor<stillElevatorOnAnotherFloor.getCurrentFloor() && direction==Direction.UP)) {
				pickupFloorToTurnBackFrom = floor;
			} 
			
			stillElevatorOnAnotherFloor.addDestFloor(floor,pickupFloorToTurnBackFrom);
			System.out.println("Elevator "+stillElevatorOnAnotherFloor.getElevatorId()+" is free on floor " 
								+stillElevatorOnAnotherFloor.getCurrentFloor()+". It will go to requested floor.");
			return true;
		}
		
		System.out.println("No elevator free to pickup right now. Adding pickup request to Queue.");
		return false;
	}

	@Override
	public void step() {
		
		/*
		 * Move each elevator by one step
		 */
		for(Elevator elevator: elevatorList) {
			elevator.moveOneStep();
		}
		
		System.out.println("\n");
		for(int i=0;i<elevatorList.size();i++) {
			ElevatorState status = status(i);
			System.out.println("elevator id: " + status.elevatorId
					+ " currentFloor: " + status.currentFloorNum
					+ " nextDestFloor: " + status.nextDestFloorNum
					+ " Direction: " + status.direction);
		}
		
		/*
		 * At every step, check if the pending pickup requests can be assigned to any elevator
		 */
		Iterator<ElevatorRequest> iter = requests.iterator();
		while(iter.hasNext()) {
			ElevatorRequest request = iter.next();
			if(findAndAllocateBestElevator(request.floor, request.direction)) {
				iter.remove();
			}
		}
	}
}

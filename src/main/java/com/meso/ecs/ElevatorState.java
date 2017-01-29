package com.meso.ecs;

import com.meso.ecs.enums.Direction;

public class ElevatorState {
	
	public int elevatorId;
	public int currentFloorNum;
	public int nextDestFloorNum;
	public Direction direction;
	
	public ElevatorState(int elevatorId, int currentFloorNum, int destFloorNum, Direction direction) {
		this.elevatorId = elevatorId;
		this.currentFloorNum = currentFloorNum;
		this.nextDestFloorNum = destFloorNum;
		this.direction = direction;
	}
}

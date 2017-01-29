package com.meso.ecs;

import com.meso.ecs.enums.Direction;

public class ElevatorRequest {
	
	public int floor;
	public Direction direction;
	
	public ElevatorRequest(int floor, Direction direction) {
		this.floor = floor;
		this.direction = direction;
	}
}

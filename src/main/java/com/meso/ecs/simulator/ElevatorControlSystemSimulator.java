package com.meso.ecs.simulator;

import com.meso.ecs.ElevatorState;
import com.meso.ecs.enums.Direction;
import com.meso.ecs.impl.ElevatorControlSystemImpl;

public class ElevatorControlSystemSimulator {
	
	static ElevatorControlSystemImpl ecs;
	
	public static void main(String[] args) {
		ecs = new ElevatorControlSystemImpl(3);
		
		for(int i=0;i<ecs.elevatorList.size();i++) {
			ElevatorState status = ecs.status(i);
			System.out.println("elevator id: " + status.elevatorId
					+ " currentFloor: " + status.currentFloorNum
					+ " nextDestFloor: " + status.nextDestFloorNum
					+ " Direction: " + status.direction);
		}
		System.out.println("\n");
		
		ecs.pickup(3, Direction.DOWN);
		ecs.pickup(1, Direction.UP);
		ecs.pickup(4, Direction.UP);
		ecs.update(1, 2);
		stepNTimes(2);
		ecs.update(0, 2);
		ecs.step();
		ecs.update(2, 6);
		stepNTimes(3);
		ecs.pickup(6, Direction.DOWN);
		ecs.update(2, 2);
		stepNTimes(5);
	}
	
	private static void stepNTimes(int n) {
		while(n-- > 0) 
			ecs.step();
	}
}


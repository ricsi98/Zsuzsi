package agent.controllers;

import agent.Agent;
import agent.IController;
import math.vec2;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

public class GridWalker implements IController {

    public enum State { FORWARD, TURN_RIGHT, TURN_LEFT, BACKWARD }

    private vec2 position;
    private int distance;
    private int turn;
    private static final int AGENT_SIZE = 20;
    private static final int TURN_AMOUNT = 90;
    private Queue<State> trajectory;

    public GridWalker() {
        position = vec2.ZERO;
        distance = 0;
        turn = 0;
        trajectory = new ArrayDeque<>();
    }

    public void addTrajectory(List<State> t) {
        for (State d : t) {
            trajectory.add(d);
        }
    }

    @Override
    public Agent.ZsuzsiActionState getNextState(Agent.ZsuzsiActionState currentState, List<Double> distances, vec2 position, vec2 dir) {
        State exploreState = trajectory.peek();
        if (exploreState == null) {
            return Agent.ZsuzsiActionState.IDLE;
        }

        Agent.ZsuzsiActionState ret = Agent.ZsuzsiActionState.IDLE;

        if (exploreState == State.FORWARD) {
            if (distance == AGENT_SIZE) {
                System.out.println("DONE");
                System.out.println(position);
                trajectory.poll();
                distance = 0;
            } else {
                ret = Agent.ZsuzsiActionState.GO_FORWARD;
                distance++;
            }
        } else if (exploreState == State.BACKWARD) {
            if (distance == AGENT_SIZE) {
                trajectory.poll();
                distance = 0;
            } else {
                ret = Agent.ZsuzsiActionState.GO_FORWARD;
                distance++;
            }
        } else if (exploreState == State.TURN_RIGHT) {
            if (turn == TURN_AMOUNT) {
                trajectory.poll();
                turn = 0;
            } else {
                ret = Agent.ZsuzsiActionState.ROTATE_RIGHT;
                turn++;
            }
        } else if (exploreState == State.TURN_LEFT) {
            if (turn == TURN_AMOUNT) {
                trajectory.poll();
                turn = 0;
            } else {
                ret = Agent.ZsuzsiActionState.ROTATE_LEFT;
                turn++;
            }
        }

        return ret;
    }
}

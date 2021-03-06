package agent.controllers;

import agent.Agent;
import agent.IController;
import math.vec2;

import java.util.List;

public class RightTurner implements IController {
    private final static int TRESHOLD = 150;
    @Override
    public Agent.ZsuzsiActionState getNextState(Agent.ZsuzsiActionState currentState, List<Double> distances, vec2 pos, vec2 dir) {
        if (distances.get(2) < TRESHOLD ||distances.get(4) < TRESHOLD || distances.get(3) < TRESHOLD) {
            return Agent.ZsuzsiActionState.ROTATE_RIGHT;
        }
        return Agent.ZsuzsiActionState.GO_FORWARD;
    }
}
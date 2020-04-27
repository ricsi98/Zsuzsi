package agent.controllers;

import agent.Agent;
import agent.IController;
import math.vec2;

import java.util.List;

public class CircleController implements IController {
    @Override
    public Agent.ZsuzsiActionState getNextState(Agent.ZsuzsiActionState currentState, List<Double> distances, vec2 pos, vec2 dir) {
        if (currentState == Agent.ZsuzsiActionState.GO_FORWARD) {
            return Agent.ZsuzsiActionState.ROTATE_RIGHT;
        }
        return Agent.ZsuzsiActionState.GO_FORWARD;
    }
}

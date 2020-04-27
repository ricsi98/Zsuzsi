package agent.controllers;

import agent.Agent;
import agent.IController;
import math.vec2;

import java.util.List;

public class ForwardController implements IController {
    private int cnt = 0;
    @Override
    public Agent.ZsuzsiActionState getNextState(Agent.ZsuzsiActionState currentState, List<Double> distances, vec2 pos, vec2 dir) {
        if (cnt < 100) {
            cnt++;
            return Agent.ZsuzsiActionState.ROTATE_LEFT;
        }

        return Agent.ZsuzsiActionState.GO_FORWARD;
    }
}

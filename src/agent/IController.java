package agent;

import math.vec2;

import java.util.List;

public interface IController {
    public Agent.ZsuzsiActionState getNextState(Agent.ZsuzsiActionState currentState, List<Double> distances, vec2 position, vec2 dir, double sensorSpread);
}

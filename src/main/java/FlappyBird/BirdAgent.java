package FlappyBird;

import Evolution.Agent;
import Evolution.Constants;

public class BirdAgent extends Agent {
    public Bird bird;
    public boolean isAlive;

    protected BirdAgent(Constants Constants, int initialMutation) {
        super(Constants, initialMutation);
        bird = new Bird();
        isAlive = true;
    }

    @Override
    public void reset() {
        isAlive = true;
        this.bird.reset();
    }
}

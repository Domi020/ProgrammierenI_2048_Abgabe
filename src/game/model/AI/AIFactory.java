package game.model.AI;

import game.AIType;
import game.model.intf.AI;
import game.model.intf.IMatrix;

/**
 * Factory class, which gives a AI object depending on the chosen AI type.
 *
 * @author Dominik Sauerer
 * @version 1.0.0
 */
public class AIFactory {

    private static AIType currentAI = AIType.RANDOM;

    public static AI getCurrentAI(IMatrix matrix) {
        if (currentAI == AIType.RANDOM) return new RandomAI(matrix);
        else if (currentAI == AIType.COOPERATIVE) return new CooperativeAI(matrix);
        else if (currentAI == AIType.MINMAX) return new MinMaxAI(matrix);
        else return new MinMaxMulti(matrix);
    }

    public static void setCurrentAI(AIType type) {
        currentAI = type;
    }

    public static AIType getCurrentAIType() {
        return currentAI;
    }
}

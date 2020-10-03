package tribonachi;

import java.util.function.IntUnaryOperator;
import java.util.function.UnaryOperator;

public class Tribonachi implements IntUnaryOperator {
    private final int a1 = (int)Math.round(Math.pow(19+3*Math.pow(33, 2), 3));
    private final int a2 = (int)Math.round(Math.pow(19-3*Math.pow(33, 2), 3));
    private final int b = (int)Math.round(Math.pow(586+102*Math.pow(33, 2), 3));

    public Tribonachi() {
    }

    @Override
    public int applyAsInt(int operand) {
        return (int)Math.round(3*b*(Math.pow((a1+a2+1)/3, operand))/(b*b-2*b+4));
    }
}

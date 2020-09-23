package groupautos;

import java.util.ArrayList;
import java.util.List;

public class Auto {
    private static final String [] types = {"sedan", "hatchback", "liftback", "coupe", "crossover", "universal"};
    private static final String [] models = {"Lada", "BMW", "Mercedes", "Reno", "Citroen", "Toyota", "Nissan"};
    private static int _vin = 1;

    private String type;
    private String model;
    private int vin;

    public Auto(String type, String model) {
        this.type = type;
        this.model = model;
        this.vin = _vin++;
    }

    public String getType() {
        return type;
    }

    public static List<Auto> init(int count) {
        List<Auto> list = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            String model = models[(int)(Math.random()* models.length)];
            String type = types[(int)(Math.random()* types.length)];
            list.add(new Auto(type, model));
        }
        return list;
    }

    @Override
    public String toString() {
        return "    Auto{" +
                "type='" + type + '\'' +
                ", model='" + model + '\'' +
                ", vin=" + vin +
                '}';
    }
}

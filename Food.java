import java.util.Random;

public class Food {

    int x;
    int y;
    Random random;

    Food() {
        random = new Random();
        generate();
    }

    void generate() {
        x = random.nextInt(600 / 25) * 25;
        y = random.nextInt(600 / 25) * 25;
    }
}
public class Snake {

    int[] x = new int[100];
    int[] y = new int[100];

    int bodyParts = 5;
    char direction = 'R';

    Snake() {
        for (int i = 0; i < bodyParts; i++) {
            x[i] = 100 - (i * 25);
            y[i] = 100;
        }
    }

    public void move() {

        for (int i = bodyParts; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

        switch (direction) {
            case 'U':
                y[0] -= 25;
                break;
            case 'D':
                y[0] += 25;
                break;
            case 'L':
                x[0] -= 25;
                break;
            case 'R':
                x[0] += 25;
                break;
        }
    }
}
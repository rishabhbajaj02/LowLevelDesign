public class Command{

    private Source source;

    private Direction direction;

    private int targetFloor;

    public Command(Source source, Direction direction, int targetFloor) {
        this.source = source;
        this.direction = direction;
        this.targetFloor = targetFloor;
    }

    public int getFloor() {
        return targetFloor;
    }

    public Source getSource() {
        return source;
    }

    public Direction getDirection() {
        return direction;
    }

}
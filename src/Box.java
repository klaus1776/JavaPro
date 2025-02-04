public class Box {
    private Object object;

    public Box(Object object) {
        this.object = object;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    @Override
    public String toString() {
        return "Box{" +
                "object=" + object.getClass().getSimpleName() +
                '}';
    }

    public static void main(String[] args) {
        Box box1 = new Box(10);
        Box box2 = new Box(20);
        System.out.println(box1);
        System.out.println(box2);

        int sum = (Integer)box1.object + (Integer)box2.object;
        System.out.println(sum);

        //box2.setObject("String new");
        if (box1.getObject() instanceof Integer && box2.getObject() instanceof Integer) {
            int sum2 = (Integer)box1.object + (Integer)box2.object;
            System.out.println(sum2);
        } else {
            System.out.println("Types error");
        }
    }
}

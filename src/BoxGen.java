public class BoxGen<T> {
    private T obj;

    public BoxGen(T obj) {
        this.obj = obj;
    }

    public T getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }

    @Override
    public String toString() {
        return "BoxGen{" +
                "obj=" + obj.getClass().getSimpleName() +
                '}';
    }

    public static void main(String[] args) {
        BoxGen<Integer> intBox1 = new BoxGen<>(10);
        BoxGen<Integer> intBox2 = new BoxGen<>(20);

        // Ошибка не возникает
        int sum = intBox1.getObj() + intBox2.getObj();
        System.out.println(sum);

        BoxGen<String> stringBoxGen = new BoxGen<>("Example");
        String ojj = stringBoxGen.getObj();

        //intBox2.setObj("String arg");
    }
}

import java.util.*;

class Box <T extends Fruit> {

    private final List<T> list;
    @SafeVarargs
    public Box(T...x) {
        this.list = new ArrayList(Arrays.asList(x));
    }

    public List<T> getList() {
        return list;
    }
    void add(T obj) {
        this.list.add(obj);
    }
      <F extends T>  void moveFrom(Box<F> box) {
        if (box != this) {
            list.addAll(box.getList());
            box.getList().clear();
        }
    }
    void moveFrom(BananBox<Banan> box){
        if (box != this) {
            list.addAll((Collection<? extends T>) box.getList());
            box.getList().clear();
        }
    }
    void info(){
        if (list.isEmpty()) {
            System.out.println("Коробка пустая");
        } else {
            System.out.println("Коробка");
            list.forEach(System.out::println);
        }
    }

    float getWeight(){
        if (list.isEmpty()) {
            return 0;
        } else {
            return list.size() * list.get(0).getWeight();
        }
    }

    boolean compare(Box<? extends Fruit> box){
        return Math.abs(this.getWeight() - box.getWeight()) < 0.001;
    }

}

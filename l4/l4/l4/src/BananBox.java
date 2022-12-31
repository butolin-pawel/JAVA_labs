import java.util.*;

public class BananBox<T extends Banan> extends Box {

    public BananBox(Fruit...x) {
        super(x);
        this.list = new ArrayList(Arrays.asList(x));
    }
    private final List<Fruit> list;

    @Override
    public List<Fruit> getList() {
        return list;
    }

     public void BmoveFrom(Box<?> box){
        if (box != this) {
            for (Fruit f:
                 box.getList()) {
                this.add(f);
            }
            box.getList().clear();
        }
    }
    }


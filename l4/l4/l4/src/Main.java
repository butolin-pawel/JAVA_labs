
public class Main {
    public static void main(String[] args) {
        Box<Orange> box = new Box<>(new Orange(), new Orange(), new Orange());
        Box<Orange> box1 = new Box<>(new Orange(),new Orange());
        Box<Apple> appleBox = new Box<>(new Apple(), new Apple());
        BananBox<Banan> bananBox = new BananBox<>(new Banan(), new Banan(), new Apple());
        box.info();
        bananBox.info();
        bananBox.BmoveFrom(box); //апельсины в бананы
        bananBox.BmoveFrom(appleBox);  //яблоки в бананы
        bananBox.info();
        box.moveFrom(box1); //апельсины в апельсины
//        appleBox.moveFrom(box); //нельзя пересыпать апельсины в яблоки
//        box.moveFrom(appleBox); //нельзя пересыпать яблоки в апельсины
//        box.moveFrom(bananBox); //нельзя пересыпать бананы в апельсины
//        appleBox.moveFrom(bananBox); //нельзя пересыпать бананы в яблоки
//          box.add(new Apple()); //нельзя добавить яблоко в апельсины
//          box.add(new Banan()); //нельзя добавить банан в апельсины
//          appleBox.add(new Orange()); //нельзя добавить апельсин в яблоки
//          appleBox.add(new Banan()); //нельзя добавить банан в яблоки
          bananBox.add(new Banan());
          bananBox.add(new Orange());
          bananBox.add(new Apple());
        box.add(new Orange());
          appleBox.add(new Apple());
        box.info();
        appleBox.info();
        bananBox.info();
    }
}
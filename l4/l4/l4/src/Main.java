
public class Main {
    public static void main(String[] args) {
        Box<Orange> box = new Box<>(new Orange(), new Orange(), new Orange());
        Box<Orange> box1 = new Box<>(new Orange(),new Orange());
        Box<Apple> appleBox = new Box<>(new Apple(), new Apple());
        BananBox<Banan> bananBox = new BananBox<>(new Banan(), new Banan(), new Apple());
        box.info();
        bananBox.info();
        bananBox.BmoveFrom(box); //��������� � ������
        bananBox.BmoveFrom(appleBox);  //������ � ������
        bananBox.info();
        box.moveFrom(box1); //��������� � ���������
//        appleBox.moveFrom(box); //������ ���������� ��������� � ������
//        box.moveFrom(appleBox); //������ ���������� ������ � ���������
//        box.moveFrom(bananBox); //������ ���������� ������ � ���������
//        appleBox.moveFrom(bananBox); //������ ���������� ������ � ������
//          box.add(new Apple()); //������ �������� ������ � ���������
//          box.add(new Banan()); //������ �������� ����� � ���������
//          appleBox.add(new Orange()); //������ �������� �������� � ������
//          appleBox.add(new Banan()); //������ �������� ����� � ������
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
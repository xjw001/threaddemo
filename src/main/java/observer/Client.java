package observer;

public class Client {
    public static void main(String[] args) {
        ConcreteSubject cs = new ConcreteSubject();
        Observer o = new ConcreteObserver();
        cs.addObserver(o);
        cs.doSomething();
    }
}

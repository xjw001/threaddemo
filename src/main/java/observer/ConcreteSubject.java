package observer;

public class ConcreteSubject extends Subject {

    public void doSomething(){
        super.notifyObserver();
    }
}

package observer;

import java.util.Vector;

public class Subject {
    private Vector<Observer> ovector = new Vector();

    public void addObserver(Observer observer){
        this.ovector.add(observer);
    }

    public void deleteObserver(Observer observer){
        this.ovector.remove(observer);
    }

    public void notifyObserver(){
        for (Observer observer: ovector){
            observer.update();
        }
    }
}

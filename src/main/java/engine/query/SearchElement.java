package engine.query;

public class SearchElement<T> {
    private T element;
    private float distance;

    public SearchElement(T element, float distance){
        this.element = element;
        this.distance = distance;
    }

    public double getDistance(){
        return distance;
    }

    public T getElement(){
        return element;
    }

}

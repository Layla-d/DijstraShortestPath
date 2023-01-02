package sample;

import java.util.LinkedList;

public class City implements Comparable<City> {

    int x, y, i;
    LinkedList<Road> neighbors = new LinkedList<>();

    City previous;
    double distance = Double.MAX_VALUE;
    boolean visited = false;

    public City(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public City(int i, int x, int y) {
        this.x = x;
        this.y = y;
        this.i = i;
    }

    @Override
    public String toString() {
        return "City no. " + i  ;//+ " {" +
               // "x=" + x +
                //", y=" + y +
                //", i=" + i +
                //'}';
    }

    @Override
    public int compareTo(City otherCity) {
        if (this.distance < otherCity.distance) return -1;
        else if (this.distance > otherCity.distance) return 1;
        else return 0;
    }
}

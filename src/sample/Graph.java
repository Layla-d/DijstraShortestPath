package sample;

import javafx.scene.control.Control;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Graph {

    public City[] cities;
    double minX = Integer.MAX_VALUE, minY = Integer.MAX_VALUE, maxX = -1, maxY = -1;

    Graph() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("data/USA.txt"));
        int noOfCities = scanner.nextInt();
        int noOfRoads = scanner.nextInt();

        cities = new City[noOfCities];

        scanner.nextLine();

        for (int i = 0; i < noOfCities; i++) {
            String[] split = scanner.nextLine().trim().split("\\s+");
            int x = Integer.parseInt(split[1]), y = Integer.parseInt(split[2]);
            City city = new City(i, x, y);
            cities[i] = city;

            minX = Math.min(minX, x);
            minY = Math.min(minY, y);
            maxX = Math.max(maxX, x);
            maxY = Math.max(maxY, y);
        }

        scanner.nextLine();

        for (int i = 0; i < noOfRoads; i++) {
            String[] split = scanner.nextLine().trim().split("\\s+");
            int c1 = Integer.parseInt(split[0]);
            int c2 = Integer.parseInt(split[1]);
            double weight = Road.calculateEuclidean(cities[c1], cities[c2]);
            Road road1 = new Road(c2, weight);
            cities[c1].neighbors.add(road1);
            Road road2 = new Road(c1, weight);
            cities[c2].neighbors.add(road2);
        }
    }

    public double findShortestDistanceBetween(int sourceCity, int targetCity) {
//        DijkstraNode[] table = new DijkstraNode[cities.length];
//        table[sourceCity] = new DijkstraNode(sourceCity);
//        table[sourceCity].distance = 0;
//        while(hasUnvisitedNodes(table)){
//            DijkstraNode currentlyExploringItsNeighbors = bringUnvisitedNodeWithSmallestDistance();
//            for (Road road : cities[currentlyExploringItsNeighbors.cityIndex].neighbors) {
//                DijkstraNode neighborNode = table[road.connectedCity];
//                if(neighborNode.visited) continue;
//                if(neighborNode.distance > currentlyExploringItsNeighbors.distance + road.weight){
//                    neighborNode.distance = currentlyExploringItsNeighbors.distance + road.weight;
//                    neighborNode.previous = currentlyExploringItsNeighbors;
//                }
//            }
//            currentlyExploringItsNeighbors.visited = true;
//            if(currentlyExploringItsNeighbors.cityIndex == targetCity) break;
//        }
//        return table[targetCity].distance;
//    }


        PriorityQueue<City> queue = new PriorityQueue<>();
        cities[sourceCity].distance = 0;
        queue.add(cities[sourceCity]);
        while (!queue.isEmpty()) {
            City currentNode = queue.poll();
            currentNode.visited = true;
            if (currentNode == cities[targetCity]) {
                System.out.println("gonna break with " + currentNode);
                break;
            }

            for (Road road : currentNode.neighbors) {
                System.out.println("checking a neighbor");
                City connectedCity = cities[road.connectedCity];
                if (connectedCity.visited) continue;
                System.out.println(connectedCity.distance + " : " + currentNode.distance + " : " + road.weight);
                if (connectedCity.distance > currentNode.distance + road.weight) {
                    queue.remove(connectedCity);
                    connectedCity.distance = currentNode.distance + road.weight;
                    connectedCity.previous = currentNode;
                    queue.add(connectedCity);

                }
                System.out.println("after: " + connectedCity.distance + " : " + currentNode.distance + " : " + road.weight);
            }
        }
        return cities[targetCity].distance;

    }

    String  findTotalPath(int sourceCity, int targetCity) {
        
        City iteratorCity = cities[targetCity];
        String res = " " + iteratorCity.toString() + " ";
        while (iteratorCity != cities[sourceCity]) {

            res =  iteratorCity.previous  + " --" +  String.format("%.2f", iteratorCity.distance) +  "--> " +" to " +  res + "\n";
            iteratorCity= iteratorCity.previous;


        }



        return res;
    }




}


class Road {

    int connectedCity;
    double weight;

    public Road(int connectedCity, double weight) {
        this.connectedCity = connectedCity;
        this.weight = weight;
    }

    public static double calculateEuclidean(City c1, City c2) {
        double deltaX = c2.x - c1.x;
        double deltaY = c2.y - c1.y;
        return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }
}


package org.common.structs;


public class DbReadRequest {
    //    private HashMap<String, Pair<Point, Double>> clientsZoom;
    private int numberOfClients;
    private int numberOfQueriesForClient;

    public DbReadRequest(int numberOfClients, int numberOfQueriesForClient) {
        this.numberOfClients = numberOfClients;
        this.numberOfQueriesForClient = numberOfQueriesForClient;
    }

    public DbReadRequest() {

    }

    public int getNumberOfClients() {
        return numberOfClients;
    }

    public int getNumberOfQueriesForClient() {
        return numberOfQueriesForClient;
    }

    @Override
    public String toString() {
        return "Read request: number of clients: " + numberOfClients + ", number of queries per client: " + numberOfQueriesForClient;
    }
}

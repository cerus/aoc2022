package aoc.day16;

import java.util.List;

public class Valve {

    private final String identifier;
    private final int flowRate;
    private final List<String> connections;
    private boolean open;

    public Valve(final String identifier, final int flowRate, final List<String> connections) {
        this.identifier = identifier;
        this.flowRate = flowRate;
        this.connections = connections;
    }

    public String getIdentifier() {
        return this.identifier;
    }

    public int getFlowRate() {
        return this.flowRate;
    }

    public List<String> getConnections() {
        return this.connections;
    }

    public boolean isClosed() {
        return !this.isOpen();
    }

    public boolean isOpen() {
        return this.open;
    }

    public void open() {
        this.open = true;
    }

    public void close() {
        this.open = false;
    }

}

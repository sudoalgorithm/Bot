package bpdc.kunalmalhotra.workspace.cardashboard;


public class Input {

    private final boolean SIDE = true;

    private String text;

    public Input(String text) {
        this.text = text;
    }

    public boolean isSIDE() {
        return SIDE;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return text;
    }
}

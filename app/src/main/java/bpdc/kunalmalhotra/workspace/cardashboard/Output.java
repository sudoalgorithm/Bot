package bpdc.kunalmalhotra.workspace.cardashboard;

public class Output {
    private final boolean SIDE = false;
    private String text;
    private double confidenceScore;

    public Output(String text) {
        this.text = text;
    }

    public void setConfidenceScore(double confidenceScore) {
        this.confidenceScore = confidenceScore;
    }

    public boolean isSIDE() {
        return SIDE;
    }

    public String getText() {
        return text;
    }

    public double getConfidenceScore() {
        return confidenceScore;
    }

    @Override
    public String toString() {
        return text;
    }
}

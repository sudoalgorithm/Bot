package bpdc.kunalmalhotra.workspace.cardashboard;

import java.util.ArrayList;
import java.util.List;


public class Session {
    //Connectors
    private ConversationConnector conversationConnector;

    private Input lastInput;

    private ArrayList<Input> allInputs;
    private ArrayList<Output> allOutputs;

    public Session() {

        conversationConnector = new ConversationConnector(this);

        allInputs = new ArrayList<Input>();
        allOutputs = new ArrayList<Output>();
    }

    public void addInput(String text) {
        Input input = new Input(text);
        CarDashboardBotActivity.getBBInstance().addMessage(input);
        allInputs.add(input);
        lastInput = input;
            conversationConnector.sendInput(text);
    }

    public void addOutput(List<String> text) {
            Output output = new Output(text.get(0));
            allOutputs.add(output);
            CarDashboardBotActivity.getBBInstance().addMessage(output);
    }


    public Input getLastInput() {
        return lastInput;
    }

    public void setLastInput(Input lastInput) {
        this.lastInput = lastInput;
    }

}

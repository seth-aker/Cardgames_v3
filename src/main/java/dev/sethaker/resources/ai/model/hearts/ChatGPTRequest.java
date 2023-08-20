package dev.sethaker.resources.ai.model.hearts;




import dev.sethaker.resources.ai.model.Message;

import java.util.ArrayList;
import java.util.List;

public class ChatGPTRequest {

    private String model = "gpt-3.5-turbo";
    private List<Message> messages = new ArrayList<>();
    private double temperature = 1;

    public void setPrompt(String cardsAlreadyPlayed, String prompt){
        if(cardsAlreadyPlayed.equals("")){
            cardsAlreadyPlayed = "You are going first. You must play the 2C if you have it.";
        } else {
            cardsAlreadyPlayed += " You only can play cards that match the first card played if you have one. ";
        }

        messages.add(new Message("system", "You are a player in a game of hearts."));
        messages.add(new Message("user", cardsAlreadyPlayed));
        messages.add(new Message("user", prompt));

    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }
}

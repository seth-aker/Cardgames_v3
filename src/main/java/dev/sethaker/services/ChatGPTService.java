package dev.sethaker.services;


import dev.sethaker.resources.ai.model.ChatGPTResponse;
import dev.sethaker.resources.ai.model.hearts.ChatGPTRequest;
import dev.sethaker.resources.game.model.PlayerHand;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;


public class ChatGPTService implements Input {

    public static String API_URL = "https://api.openai.com/v1/chat/completions";
    private final RestTemplate restTemplate = new RestTemplate();
    String chatGPTCardChoice;

    public String requestCardSelection(String previousCardsPlayed, String query) {

        ChatGPTRequest chatGPTRequest = new ChatGPTRequest();

        chatGPTRequest.setPrompt(previousCardsPlayed, query);

        ChatGPTResponse chatGPTResponse = null;
        HttpEntity<ChatGPTRequest> entity = makeEntity(chatGPTRequest);

        try {
            chatGPTResponse = restTemplate.postForObject(API_URL, entity, ChatGPTResponse.class);

            chatGPTCardChoice = chatGPTResponse.getChoices().get(0).getMessage().getContent();

        } catch (
                RestClientResponseException e) {
            String errorMessage = "Return status: " + e.getRawStatusCode() + "\n" +
                    "Status message: " + e.getMessage();
            System.out.println(errorMessage);

        } catch (
                ResourceAccessException e) {
            String errorMessage = e.getMessage();
            System.out.println(errorMessage);

        } catch (
                NullPointerException e) {
            System.out.println("Error, no response from ChatGPT.");

        } catch (
                Exception e) {
            System.out.println("Something went wrong. " + e.getMessage());
        }
        return chatGPTCardChoice;
    }

    private HttpEntity<ChatGPTRequest> makeEntity(ChatGPTRequest chatGPTRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer " + System.getenv("OPENAI_API_KEY"));
        return new HttpEntity<>(chatGPTRequest, headers);
    }

    @Override
    public String requestCardSelection(PlayerHand playerHand) {
        return null;
    }
}

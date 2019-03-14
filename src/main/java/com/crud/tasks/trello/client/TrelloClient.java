package com.crud.tasks.trello.client;

import com.crud.tasks.domain.TrelloBoardDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;
import java.util.*;

@Component
public class TrelloClient {

    private final String trelloApiEndpoint;

    private final String trelloAppKey;

    private final String trelloToken;

    private final String trelloUsername;

    private final RestTemplate restTemplate;

    @Autowired
    public TrelloClient(@Value("${trello.api.endpoint.prod}") String trelloApiEndpoint,
                        @Value("${trello.app.key}") String trelloAppKey,
                        @Value("${trello.app.token}") String trelloToken,
                        @Value("${trello.app.username}") String trelloUsername, RestTemplate restTemplate) {
        this.trelloApiEndpoint = trelloApiEndpoint;
        this.trelloAppKey = trelloAppKey;
        this.trelloToken = trelloToken;
        this.trelloUsername = trelloUsername;
        this.restTemplate = restTemplate;
    }

    public List<TrelloBoardDto> getTrelloBoards() {

        URI url = buildUrl();

        try{
            TrelloBoardDto[] boardsResponse = restTemplate.getForObject(url, TrelloBoardDto[].class);
            return Optional.ofNullable(boardsResponse).map(Arrays::asList).orElse(Collections.emptyList());
        }catch(RuntimeException e){
            System.out.println("type of RestClientResponseException: " + e);
            return new ArrayList<>();
        }
    }

    private URI buildUrl(){

        return UriComponentsBuilder.fromHttpUrl(String.format("%s/members/%s/boards", trelloApiEndpoint, trelloUsername))
                .queryParam("key", trelloAppKey)
                .queryParam("token", trelloToken)
                .queryParam("fields", "name,id")
                .build().encode().toUri();
    }
}
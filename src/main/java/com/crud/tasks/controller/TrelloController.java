package com.crud.tasks.controller;

import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.trello.client.TrelloClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/trello")
public class TrelloController {

    private final TrelloClient trelloClient;

    @Autowired
    public TrelloController(TrelloClient trelloClient) {
        this.trelloClient = trelloClient;
    }

    @RequestMapping(method = RequestMethod.GET, value = "getTrelloBoards")
    public List<TrelloBoardDto> getTrelloBoards() {

        return trelloClient.getTrelloBoards().stream()
                .filter(board -> board.getId() != null)
                .filter(board -> board.getName().contains("Kodilla"))
                .collect(Collectors.toList());

        //filtredTrelloBoards.forEach(trelloBoardDto -> System.out.println(trelloBoardDto.getId() + " " + trelloBoardDto.getName()));

    }
}
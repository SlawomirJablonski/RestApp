package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.*;
import com.crud.tasks.trello.client.TrelloClient;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TrelloServiceTest {

    private static final String SUBJECT = "Tasks: New Trello card";

    @InjectMocks
    private TrelloService trelloService;
    @Mock
    private AdminConfig adminConfig;
    @Mock
    private TrelloClient trelloClient;
    @Mock
    private SimpleEmailService emailService;

    @Test
    public void fetchTrelloBoardsTest() {
        //Given
        List<TrelloListDto> trelloLists = new ArrayList<>();
        trelloLists.add(new TrelloListDto("1","my_list",false));

        List<TrelloBoardDto> trelloBoards = new ArrayList<>();
        trelloBoards.add(new TrelloBoardDto("my_task","1",trelloLists));

        when(trelloClient.getTrelloBoards()).thenReturn(trelloBoards);

        //When
        List<TrelloBoardDto> trelloBoardDtos = trelloService.fetchTrelloBoards();
        TrelloBoardDto theBoardDto = trelloBoardDtos.get(0);
        List<TrelloListDto> listsOnTheBoard = theBoardDto.getLists();

        //Then
        assertNotNull(trelloBoardDtos);
        assertEquals(1,trelloBoardDtos.size());
        assertEquals(1,listsOnTheBoard.size());
    }

    @Test
    public void createTrelloCardTest() {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("code","to develop","1","v1");
        CreatedTrelloCardDto newCard = new CreatedTrelloCardDto(
                "001","Ecommercee","http://localhost:8080/v1/order/getOrders");

        when(trelloClient.createNewCard(trelloCardDto)).thenReturn(newCard);

        //When
        CreatedTrelloCardDto testCard = trelloService.createTrelloCard(trelloCardDto);

        //Then
        assertNotNull(testCard);
        Assert.assertEquals(testCard.hashCode(),newCard.hashCode());
        Assert.assertEquals(testCard,newCard);
    }
}
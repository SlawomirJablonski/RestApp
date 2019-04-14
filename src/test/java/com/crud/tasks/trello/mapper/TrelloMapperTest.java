package com.crud.tasks.trello.mapper;

import org.apache.commons.lang3.StringUtils;
import com.crud.tasks.domain.*;
import com.crud.tasks.mapper.TrelloMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import java.util.*;

@RunWith(MockitoJUnitRunner.class)
public class TrelloMapperTest {

    @InjectMocks
    private TrelloMapper trelloMapper;

    private TrelloListDto listDto1 = new TrelloListDto("1", "shopping", false);
    private TrelloListDto listDto2 = new TrelloListDto("2", "painting", false);
    private List<TrelloListDto> lists1 = Arrays.asList(listDto1, listDto2);
    private TrelloBoardDto trelloBoardDto1 = new TrelloBoardDto("ToDo", "123123", lists1);
    private TrelloListDto listDto3 = new TrelloListDto("1", "party", false);
    private TrelloListDto listDto4 = new TrelloListDto("2", "travel", false);
    private List<TrelloListDto> lists2 = Arrays.asList(listDto3, listDto4);
    private TrelloBoardDto trelloBoardDto2 = new TrelloBoardDto("Done", "456456", lists2);
    private List<TrelloBoardDto> trelloBoardDtos = Arrays.asList(trelloBoardDto1, trelloBoardDto2);

    private TrelloList runtime = new TrelloList("1", "unchecked exceptions", false);
    private TrelloList checked = new TrelloList("2", "checked exceptions", false);
    private List<TrelloList> exceptionList = Arrays.asList(runtime, checked);
    private TrelloBoard exceptions = new TrelloBoard("1", "Exception", exceptionList);
    private List<TrelloList> errorsList = Collections.emptyList();
    private TrelloBoard errors = new TrelloBoard("2", "Errors", errorsList);
    private List<TrelloBoard> throwable = Arrays.asList(errors, exceptions);

    @Test
    public void shouldMapToBoardList() {
        //Given

        //When
        List<TrelloBoard> result = trelloMapper.mapToBoardList(trelloBoardDtos);
        TrelloBoard doneBoard = result.get(1);
        TrelloList travelList = doneBoard.getLists().get(1);

        //Then
        Assert.assertEquals(2, result.size());
        Assert.assertEquals("travel", travelList.getName());
    }

    @Test
    public void shouldReturnCollectionContainsBoardWithEmptyList(){
        //Given

        //When
        List<TrelloBoardDto> result = trelloMapper.mapToBoardDtoList(throwable);
        TrelloBoardDto errorsBoard = result.get(0);

        //Then
        Assert.assertEquals(2, result.size());
        Assert.assertTrue(errorsBoard.getLists().isEmpty());
    }

    @Test
    public void shouldReturnTrelloListCollection() {
        //Given

        //When
        List<TrelloList> result = trelloMapper.mapToList(lists1);
        TrelloList firstList = result.get(0);
        TrelloList secondList = result.get(1);
        //Then
        Assert.assertEquals("shopping",firstList.getName());
        Assert.assertEquals("painting",secondList.getName());
    }

    @Test
    public void shouldReturnTrelloListDtoCollection(){
        //Given

        //When
        List<TrelloListDto> result = trelloMapper.mapToListDto(exceptionList);
        boolean isRuntimeClosed = result.get(0).isClosed();
        boolean isCheckedClosed = result.get(1).isClosed();
         //Then
        Assert.assertEquals(2, result.size());
        Assert.assertFalse(isRuntimeClosed && isCheckedClosed);
    }

    @Test
    public void shouldReturnEmptyCard(){
        //Given
        TrelloCard fakeCard = new TrelloCard(null,null,"","");
        //When
        TrelloCardDto result = trelloMapper.mapToCardDto(fakeCard);
        //Then
        Assert.assertTrue(StringUtils.isBlank(result.getName()));
        Assert.assertFalse(StringUtils.isNotBlank(result.getDescription()));
        Assert.assertTrue(StringUtils.isEmpty(result.getPos()));
        Assert.assertFalse(StringUtils.isNotEmpty(result.getListId()));
    }

    @Test
    public void shouldReturnCardWithNullAtributes(){
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto();
        //When
        TrelloCard result = trelloMapper.mapToCard(trelloCardDto);
        String name = result.getName();
        String description = result.getDescription();
        String pos = result.getPos();
        String listId = result.getListId();
        //Then
        Assert.assertNull(name);
        Assert.assertNull(description);
        Assert.assertNull(pos);
        Assert.assertNull(listId);
    }
}
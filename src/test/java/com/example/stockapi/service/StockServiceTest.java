package com.example.stockapi.service;

import com.example.stockapi.dto.StockDto;
import com.example.stockapi.entity.Stock;
import com.example.stockapi.repository.StockRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class StockServiceTest {

    @Mock
    private StockRepository stockRepository;

    @Captor
    private ArgumentCaptor<Stock> stockArgumentCaptor;

    private StockService stockService;

    @BeforeEach
    void setUp(){
        stockService = new StockServiceImpl(stockRepository);
    }

    @Test
    void getStocks() {
        // When
        stockService.getStocks();

        // Then
        verify(stockRepository).findAll();
    }

    @Test
    void getStock() {
        // given
        Stock stock = new Stock("name", 2.2);
        given(stockRepository.findById(1L)).willReturn(Optional.of(stock));
        // When
        stockService.getStock(1L);

        // Then
        verify(stockRepository).findById(1L);
    }

    @Test
    void updateStock() {
        Stock stock = new Stock("name", 2.2);
        given(stockRepository.findById(1L)).willReturn(Optional.of(stock));

        // When
        stockService.updateStock(new StockDto("new name", 2.2), 1L);

        // Then
        verify(stockRepository).save(stock);
    }

    @Test
    void deleteStock() {
        // given
        Stock stock = new Stock("name", 2.2);
        given(stockRepository.findById(1L)).willReturn(Optional.of(stock));

        // When
        stockService.deleteStock(1L);

        // Then
        verify(stockRepository).deleteById(1L);
    }

    @Test
    void createStock() {
        StockDto stockDto = new StockDto("name", 2.2);
        Stock stock = new Stock(stockDto.getName(), stockDto.getCurrentPrice());

        // When
        stockService.createStock(stockDto);

        // Then
        verify(stockRepository).save(stockArgumentCaptor.capture());
    }
}
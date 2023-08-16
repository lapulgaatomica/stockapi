package com.example.stockapi.service;

import com.example.stockapi.dto.StockDto;
import com.example.stockapi.entity.Stock;
import com.example.stockapi.exception.ResourceNotFoundException;
import com.example.stockapi.repository.StockRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StockServiceImplTest {
    @Mock
    private StockRepository stockRepository;
    @InjectMocks
    private StockServiceImpl stockServiceImpl;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getStocks() {
        List<Stock> stocks = new ArrayList<>();
        stocks.add(new Stock(1L, "name 1", 11.0, Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now())));
        stocks.add(new Stock(2L, "name 2", 12.0, Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now())));
        when(stockRepository.findAll()).thenReturn(stocks);

        List<Stock> stockList = stockServiceImpl.getStocks();
        assertNotNull(stockList);
        assertEquals(2, stockList.size());
        verify(stockRepository, times(1)).findAll();
    }

    @Test
    void getStock() {
        Stock stock = new Stock(1L, "name 1", 11.0, Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now()));
        when(stockRepository.findById(1L)).thenReturn(Optional.of(stock));
        Stock result = stockServiceImpl.getStock(1L);
        assertNotNull(result);
        assertEquals("name 1", result.getName());
        assertEquals(11.0, result.getCurrentPrice());
        verify(stockRepository, times(1)).findById(1L);
    }

    @Test
    void getStockWhenObjectDoesNotExist() {
        when(stockRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> stockServiceImpl.getStock(1L));
        verify(stockRepository, times(1)).findById(1L);
    }

    @Test
    void updateStock() {
        Stock stock = new Stock(1L, "name 1", 11.0, Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now()));
        when(stockRepository.findById(1L)).thenReturn(Optional.of(stock));
        Stock stockUpdated = new Stock(1L, "name 1 Updated", 12.0, Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now()));
        when(stockRepository.save(any(Stock.class))).thenReturn(stockUpdated);
        StockDto stockDto = new StockDto("name 1 Updated", 12.0);
        Stock result = stockServiceImpl.updateStock(stockDto, 1L);
        assertNotNull(result);
        assertEquals("name 1 Updated", result.getName());
        assertEquals(12.0, result.getCurrentPrice());
        verify(stockRepository, times(1)).findById(1L);
        verify(stockRepository, times(1)).save(any(Stock.class));
    }

    @Test
    void deleteStock() {
        when(stockRepository.findById(1L)).thenReturn(Optional.of(new Stock()));
        stockServiceImpl.deleteStock(1L);
        verify(stockRepository, times(1)).deleteById(1L);
    }

    @Test
    void createStock() {
        Stock stock = new Stock(1L, "name 1", 11.0, Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now()));
        when(stockRepository.save(any(Stock.class))).thenReturn(stock);
        StockDto stockDto = new StockDto("name 1", 11.0);
        Stock saved = stockServiceImpl.createStock(stockDto);
        assertNotNull(saved);
        assertEquals("name 1", saved.getName());
        assertEquals(11.0, saved.getCurrentPrice());
        verify(stockRepository, times(1)).save(any(Stock.class));
    }
}
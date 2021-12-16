package com.example.stockapi.service;

import com.example.stockapi.dto.StockDto;
import com.example.stockapi.entity.Stock;

import java.util.List;

public interface StockService {
    List<Stock> getStocks();
    Stock getStock(Long id);
    Stock updateStock(StockDto stockDto, Long id);
    void deleteStock(Long id);
    Stock createStock(StockDto stockDto);
}

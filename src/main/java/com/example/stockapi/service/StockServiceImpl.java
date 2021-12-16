package com.example.stockapi.service;

import com.example.stockapi.dto.StockDto;
import com.example.stockapi.entity.Stock;
import com.example.stockapi.exception.ResourceNotFoundException;
import com.example.stockapi.repository.StockRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockServiceImpl implements StockService{
    private final StockRepository stockRepository;

    public StockServiceImpl(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public List<Stock> getStocks(){
        return stockRepository.findAll();
    }

    public Stock getStock(Long id){
        return stockRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("stock was not found"));
    }

    public Stock updateStock(StockDto stockDto, Long id){
        Stock stock = stockRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("stock not found"));
        if(stockDto.getCurrentPrice() != null){
            stock.setCurrentPrice(stockDto.getCurrentPrice());
        }
        if(stockDto.getName() != null && !stockDto.getName().trim().equals("")){
            stock.setName(stockDto.getName());
        }
        return stockRepository.save(stock);
    }

    public void deleteStock(Long id){
        stockRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("stock was not found"));
        stockRepository.deleteById(id);
    }

    public Stock createStock(StockDto stockDto){
        return stockRepository.save(new Stock(stockDto.getName(), stockDto.getCurrentPrice()));
    }
}

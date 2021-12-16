package com.example.stockapi.controller;

import com.example.stockapi.dto.StockDto;
import com.example.stockapi.dto.error.ErrorDetail;
import com.example.stockapi.entity.Stock;
import com.example.stockapi.service.StockService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/stocks")
public class StockController {
    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping
    @ApiOperation(value = "Retrieves all the stocks", response=Stock.class, responseContainer="List")
    public ResponseEntity<List<Stock>> getStocks(){
        return new ResponseEntity<>(stockService.getStocks(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Retrieves given Stock", response=Stock.class)
    @ApiResponses(value = {@ApiResponse(code=200, message="", response=Stock.class),
            @ApiResponse(code=404, message="Unable to find Stock", response=ErrorDetail.class) } )
    public ResponseEntity<Stock> getStock(@PathVariable Long id){
        return new ResponseEntity<>(stockService.getStock(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Updates given Stock", response=Stock.class)
    @ApiResponses(value = {@ApiResponse(code=200, message="", response=Stock.class),
            @ApiResponse(code=404, message="Unable to find Stock", response=ErrorDetail.class) } )
    public ResponseEntity<Stock> updateStock(@RequestBody StockDto stockDto, @PathVariable Long id){
        return new ResponseEntity<>(stockService.updateStock(stockDto, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Deletes given Stock")
    @ApiResponses(value = {@ApiResponse(code=200, message=""),
            @ApiResponse(code=404, message="Unable to find Stock", response=ErrorDetail.class) } )
    public ResponseEntity<Void> deleteStock(@PathVariable Long id){
        stockService.deleteStock(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping
    @ApiOperation(value = "Creates a new Stock", response = Stock.class)
    @ApiResponses(value = {@ApiResponse(code=201, message="Stock Created Successfully"),
            @ApiResponse(code=400, message="Error creating Stock", response= ErrorDetail.class) } )
    public ResponseEntity<Stock> createStock(@RequestBody @Valid StockDto stockDto){
        return new ResponseEntity<>(stockService.createStock(stockDto), HttpStatus.CREATED);
    }
}

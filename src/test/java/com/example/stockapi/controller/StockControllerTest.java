package com.example.stockapi.controller;

import com.example.stockapi.dto.StockDto;
import com.example.stockapi.entity.Stock;
import com.example.stockapi.service.StockService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@ExtendWith(SpringExtension.class)
@AutoConfigureJsonTesters
@WebMvcTest(StockController.class)
class StockControllerTest {
    @MockBean
    private StockService stockService;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<List<Stock>> stocksJson;

    @Autowired
    private JacksonTester<Stock> stockJson;

    @Autowired
    private JacksonTester<StockDto> stockDtoJson;

    @Test
    void getStocks() throws Exception {
        // given
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());

        Stock stock1 = new Stock(1L, "name1", 0.0, now, now);
        Stock stock2 = new Stock(2L, "name2", 0.0, now, now);
        List<Stock> stocks = new ArrayList<>();
        Collections.addAll(stocks, stock1, stock2);
        given(stockService.getStocks()).willReturn(stocks);

        // when
        MockHttpServletResponse response = mvc.perform(
                get("/api/stocks").accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        then(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        then(response.getContentAsString())
                .isEqualTo(stocksJson.write(stocks).getJson());
    }

    @Test
    void getStock() throws Exception {
        // given
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());

        Stock stock = new Stock(1L, "name1", 0.0, now, now);
        given(stockService.getStock(1L)).willReturn(stock);

        // when
        MockHttpServletResponse response = mvc.perform(
                        get("/api/stocks/1").accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        then(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        then(response.getContentAsString())
                .isEqualTo(stockJson.write(stock).getJson());
    }

    @Test
    void updateStock() throws Exception {
        // given
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        StockDto stockDto = new StockDto("new name", 1.1);
        Stock stock = new Stock(1L, "new name", 1.1, now, now);
        given(stockService.updateStock(stockDto, 1L)).willReturn(stock);

        // when
        MockHttpServletResponse response = mvc.perform(
                put("/api/stocks/1").contentType(MediaType.APPLICATION_JSON)
                            .content(stockDtoJson.write(stockDto).getJson()))
                .andReturn().getResponse();

        // then
        then(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void deleteStock() throws Exception {
        // When
        MockHttpServletResponse response = mvc.perform(
                delete("/api/stocks/1")).andReturn().getResponse();

        // then
        then(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void createStock() throws Exception {
        // given
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        StockDto stockDto = new StockDto("new name", 1.1);
        Stock stock = new Stock(1L, "new name", 1.1, now, now);
        given(stockService.updateStock(stockDto, 1L)).willReturn(stock);

        // when
        MockHttpServletResponse response = mvc.perform(
                        post("/api/stocks").contentType(MediaType.APPLICATION_JSON)
                                .content(stockDtoJson.write(stockDto).getJson()))
                .andReturn().getResponse();

        // then
        then(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
    }
}
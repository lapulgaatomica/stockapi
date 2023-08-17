package com.example.stockapi.controller;

import com.example.stockapi.dto.StockDto;
import com.example.stockapi.entity.Stock;
import com.example.stockapi.service.StockService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@WebMvcTest(StockController.class)
@AutoConfigureMockMvc
class StockControllerTest {
    @MockBean
    private StockService stockService;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getStocks() throws Exception {
        // given
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());

        Stock stock1 = new Stock(1L, "name1", 0.0, now, now);
        Stock stock2 = new Stock(2L, "name2", 0.0, now, now);
        List<Stock> stocks = new ArrayList<>();
        Collections.addAll(stocks, stock1, stock2);
        given(stockService.getStocks()).willReturn(stocks);
        String responseString = objectMapper.writeValueAsString(stocks);

        // when
        MockHttpServletResponse response = mvc.perform(
                get("/api/stocks").accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        then(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        then(response.getContentAsString())
                .isEqualTo(responseString);
    }

    @Test
    void getStock() throws Exception {
        // given
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());

        Stock stock = new Stock(1L, "name1", 0.0, now, now);
        String responseString = objectMapper.writeValueAsString(stock);
        given(stockService.getStock(1L)).willReturn(stock);

        // when
        MockHttpServletResponse response = mvc.perform(
                        get("/api/stocks/1").accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        then(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        then(response.getContentAsString())
                .isEqualTo(responseString);
    }

    @Test
    void updateStock() throws Exception {
        // given
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        StockDto stockDto = new StockDto("new name updated", 1.1);
        Stock stock = new Stock(1L, "new name updated", 1.1, now, now);
        String responseString = objectMapper.writeValueAsString(stock);
        given(stockService.updateStock(any(), eq(1L))).willReturn(stock);


        // when
        MockHttpServletResponse response = mvc.perform(
                put("/api/stocks/1").contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(stockDto)))
                .andReturn().getResponse();

        // then
        then(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        then(response.getContentAsString()).isEqualTo(responseString);
    }

    @Test
    void deleteStock() throws Exception {
        // When
        MockHttpServletResponse response = mvc.perform(
                delete("/api/stocks/1")).andReturn().getResponse();

        // then
        then(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        verify(stockService, times(1)).deleteStock(eq(1L));
    }

    @Test
    void createStock() throws Exception {
        // given
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        StockDto stockDto = new StockDto("new name", 1.1);
        Stock stock = new Stock(1L, "new name", 1.1, now, now);
        String responseString = objectMapper.writeValueAsString(stock);
        given(stockService.createStock(any())).willReturn(stock);

        // when
        MockHttpServletResponse response = mvc.perform(
                        post("/api/stocks").contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(stockDto)))
                .andReturn().getResponse();

        // then
        then(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        then(response.getContentAsString()).isEqualTo(responseString);
    }
}
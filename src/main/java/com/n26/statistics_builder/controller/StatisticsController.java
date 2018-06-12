package com.n26.statistics_builder.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.n26.statistics_builder.model.Statistics;
import com.n26.statistics_builder.service.TransactionService;

@RequestMapping("/statistics")
@RestController
public class StatisticsController {

	private final TransactionService transactionService;

    @Autowired
    public StatisticsController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @RequestMapping(method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody Statistics getStatistics() {
        return transactionService.getCurrentStatistics();
    }
}

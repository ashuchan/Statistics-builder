package com.n26.statistics_builder;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.boot.SpringApplication;

@SpringBootApplication
@EnableScheduling
public class StatisticsBuilder 
{
    public static void main( String[] args )
    {
    	SpringApplication.run(StatisticsBuilder.class,args);
    }
}

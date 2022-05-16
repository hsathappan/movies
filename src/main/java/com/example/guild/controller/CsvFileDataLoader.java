package com.example.guild.controller;

import com.example.guild.controller.dto.MovieData;
import com.example.guild.service.CsvFileService;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

@AllArgsConstructor
@Component
public class CsvFileDataLoader {

    CsvFileService csvFileService;

    @EventListener(ApplicationReadyEvent.class)
    public void readCsvFile() throws FileNotFoundException {
        String fileName = "movies_metadata.csv";

        List<MovieData> movieDataList = new CsvToBeanBuilder(new FileReader(fileName))
                .withType(MovieData.class)
                .build()
                .parse();

        csvFileService.loadMovieData(movieDataList);
    }
}

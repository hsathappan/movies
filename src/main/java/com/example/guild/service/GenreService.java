package com.example.guild.service;

import com.example.guild.controller.dto.GenreDto;
import com.example.guild.repository.GenreDetailsRepository;
import com.example.guild.repository.GenreRepository;
import com.example.guild.repository.MovieGenreRepository;
import com.example.guild.repository.entity.Genre;
import com.example.guild.repository.entity.reporting.GenreDetails;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@AllArgsConstructor
public class GenreService {

    GenreDetailsRepository genreDetailsRepository;
    GenreRepository genreRepository;

    public GenreDto getMostPopularGenre(int year) {
        Optional<GenreDetails> mostPopularGenreDetails = genreDetailsRepository.getMostPopularGenreDetails(year);
        if (!mostPopularGenreDetails.isPresent())
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No genre exists for the year " + year);
        Long genreId = mostPopularGenreDetails.get().getGenreId();
        Genre genre = genreRepository.getById(genreId);
        return new GenreDto(genreId, genre.getName(), mostPopularGenreDetails.get().getYear(), mostPopularGenreDetails.get().getPopularity());
    }
}

package com.example.guild.service;

import com.example.guild.controller.dto.MovieData;
import com.example.guild.repository.*;
import com.example.guild.repository.entity.*;
import com.example.guild.repository.entity.relationship.CompanyProduction;
import com.example.guild.repository.entity.relationship.MovieGenre;
import com.example.guild.repository.entity.reporting.CompanyDetails;
import com.example.guild.repository.entity.reporting.GenreDetails;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
@Slf4j
@AllArgsConstructor
@Service
public class CsvFileService {

    MovieRepository movieRepository;
    CompanyRepository companyRepository;
    GenreRepository genreRepository;
    CompanyProductionRepository companyProductionRepository;
    MovieGenreRepository movieGenreRepository;
    CompanyDetailsRepository companyDetailsRepository;
    GenreDetailsRepository genreDetailsRepository;

    Map<Long, Map<Integer, CompanyDetails>> companyDetailsPerYear;
    Set<Movie> movieSet = new HashSet<>();
    Set<Genre> genreSet = new HashSet<>();
    Set<Company> companySet = new HashSet<>();
    Set<CompanyProduction> companyProductionSet = new HashSet<>();
    Set<MovieGenre> movieGenreSet = new HashSet<>();

    public void loadMovieData(List<MovieData> movieDataList) {
        JsonFactory factory = new JsonFactory();
        factory.enable(JsonParser.Feature.ALLOW_SINGLE_QUOTES);
        ObjectMapper mapper = new ObjectMapper(factory);
        int count = 0;
        for (MovieData movieData : movieDataList) {
            if (movieData.getRelease_date().isBlank()) {
                log.warn("WARN movie date is blank for movie {}. Ignoring the record.", movieData.getTitle() );
                continue;
            }
            Movie movie = processMovie(movieData);
            movieSet.add(movie);
            List<Genre> genres = processGenres(movieData, mapper);
            genreSet.addAll(genres);
            List<Company> companies = processCompanies(movieData, mapper);
            companySet.addAll(companies);
            List<CompanyProduction> companyProductions = processCompanyProduction(movie, companies);
            companyProductionSet.addAll(companyProductions);
            List<MovieGenre> movieGenres = processMovieGenres(movie, genres);
            movieGenreSet.addAll(movieGenres);
            count++;
            if (count == 25000) {
                flushData();
                count = 0;
            }
        }

        flushData();
        if (companyDetailsPerYear != null && !companyDetailsPerYear.isEmpty()) {
            List<CompanyDetails> companyDetailsList = companyDetailsPerYear.values().stream().map((m) -> m.values())
                    .flatMap(Collection::stream)
                    .collect(Collectors.toList());
            companyDetailsRepository.saveAll(companyDetailsList);
        }

        summarizeGenreDetails();
    }

    private void summarizeGenreDetails() {
        List<GenreDetails> genreDetails = movieGenreRepository.getGenreDetails();
        if (!genreDetails.isEmpty())
            genreDetailsRepository.saveAll(genreDetails);
    }

    private void flushData() {
        if (!movieSet.isEmpty()) {
            movieRepository.saveAll(movieSet);
            movieSet.clear();
        }
        if (!companySet.isEmpty()) {
            companyRepository.saveAll(companySet);
            companySet.clear();
        }
        if (!genreSet.isEmpty()) {
            genreRepository.saveAll(genreSet);
            genreSet.clear();
        }
        if (!companyProductionSet.isEmpty()) {
            companyProductionRepository.saveAll(companyProductionSet);
            companyProductionSet.clear();
        }
        if (!movieGenreSet.isEmpty()) {
            movieGenreRepository.saveAll(movieGenreSet);
            movieGenreSet.clear();
        }
    }

    private List<MovieGenre> processMovieGenres(Movie movie, List<Genre> genres) {
        List<MovieGenre> movieGenres = new LinkedList<>();
        for (Genre genre : genres) {
            movieGenres.add(new MovieGenre(movie.getId(), genre.getId(), movie.getReleaseDate().getYear()));
        }

        return movieGenres;
    }

    private List<CompanyProduction> processCompanyProduction(Movie movie, List<Company> companies) {
        List<CompanyProduction> companyProductions = new LinkedList<>();
        for (Company company : companies) {
            companyProductions.add(new CompanyProduction(movie.getId(), company.getId(), movie.getReleaseDate().getYear()));
            addCompanyDetailsPerYear(movie, company.getId());
        }

        return companyProductions;
    }

    private void addCompanyDetailsPerYear(Movie movie, long companyId) {
        Map<Integer, CompanyDetails> yearCompanyMap = companyDetailsPerYear.getOrDefault(companyId, new HashMap<>());
        int year = movie.getReleaseDate().getYear();
        CompanyDetails companyDetails = yearCompanyMap.getOrDefault(year,
                new CompanyDetails(companyId, year, 0L, 0L));
        companyDetails.setBudget(companyDetails.getBudget() + movie.getBudget());
        companyDetails.setRevenue(companyDetails.getRevenue() + movie.getRevenue());
        yearCompanyMap.put(year, companyDetails);
        companyDetailsPerYear.put(companyId, yearCompanyMap);
    }

    private Movie processMovie(MovieData movieData) {
        Movie movie = Movie.builder()
                .id(movieData.getId())
                .budget(movieData.getBudget())
                .popularity(movieData.getPopularity())
                .releaseDate(LocalDate.parse(movieData.getRelease_date()))
                .revenue(movieData.getRevenue())
                .title(movieData.getTitle())
                .build();
        return movie;
    }
    private List<Genre> processGenres(MovieData movie, ObjectMapper mapper) {
        List<Genre> genres;
        try {
            genres = mapper.readValue(movie.getGenres(), new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            // Log error and continue processing
            log.warn("Exception parsing genres " + movie.getGenres() + " for movie " + movie.getTitle(), e);
            return List.of();
        }
        return genres;
    }

    private List<Company> processCompanies(MovieData movie, ObjectMapper mapper) {
        List<Company> companies;
        try {
            companies = mapper.readValue(movie.getProduction_companies(), new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            // Log error and continue processing
            log.warn("Exception parsing companies " + movie.getProduction_companies() + " for movie " + movie.getTitle(), e);
            return List.of();
        }
        return companies;
    }
}

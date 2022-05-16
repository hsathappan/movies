package com.example.guild.repository;

import com.example.guild.repository.entity.reporting.GenreDetails;
import com.example.guild.repository.entity.compositePK.GenreYearPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GenreDetailsRepository extends JpaRepository<GenreDetails, GenreYearPK> {
    @Query(
            "SELECT new com.example.guild.repository.entity.reporting.GenreDetails(genreId, year, popularity)  " +
            "FROM GenreDetails " +
            "WHERE year = ?1 " +
            "AND popularity = (SELECT max(popularity) FROM GenreDetails WHERE year = ?1) "
    )
    Optional<GenreDetails> getMostPopularGenreDetails(int year);
}

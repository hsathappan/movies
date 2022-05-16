package com.example.guild.repository;

import com.example.guild.repository.entity.reporting.GenreDetails;
import com.example.guild.repository.entity.relationship.MovieGenre;
import com.example.guild.repository.entity.compositePK.MovieGenrePK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieGenreRepository extends JpaRepository<MovieGenre, MovieGenrePK> {

    @Query("SELECT new com.example.guild.repository.entity.reporting.GenreDetails(mg.genreId, mg.productionYear, avg(m.popularity)) " +
            "FROM MovieGenre as mg " +
            "JOIN Movie as m on m.id = mg.movieId " +
            "GROUP BY mg.genreId, mg.productionYear"
    )
    List<GenreDetails> getGenreDetails();
}

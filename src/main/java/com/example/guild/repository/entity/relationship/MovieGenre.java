package com.example.guild.repository.entity.relationship;

import com.example.guild.repository.entity.compositePK.MovieGenrePK;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@IdClass(MovieGenrePK.class)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieGenre {
    @Id
    long movieId;
    @Id
    long genreId;
    int productionYear;
}

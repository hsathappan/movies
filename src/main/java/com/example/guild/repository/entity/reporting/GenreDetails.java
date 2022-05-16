package com.example.guild.repository.entity.reporting;

import com.example.guild.repository.entity.compositePK.GenreYearPK;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@IdClass(GenreYearPK.class)
@Entity
@Table(indexes = @Index(columnList = "year"))
public class GenreDetails {
    @Id
    Long genreId;
    @Id
    int year;
    double popularity;
}

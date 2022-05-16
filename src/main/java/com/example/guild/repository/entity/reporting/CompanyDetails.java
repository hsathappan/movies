package com.example.guild.repository.entity.reporting;

import com.example.guild.repository.entity.compositePK.CompanyYearPK;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@IdClass(CompanyYearPK.class)
@Entity
@Table(indexes = @Index(columnList = "year"))
public class CompanyDetails {
    @Id
    Long companyId;
    @Id
    int year;
    Long budget;
    Long revenue;
}

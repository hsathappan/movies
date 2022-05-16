package com.example.guild.repository;

import com.example.guild.repository.entity.reporting.CompanyDetails;
import com.example.guild.repository.entity.compositePK.CompanyYearPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyDetailsRepository extends JpaRepository<CompanyDetails, CompanyYearPK> {

}

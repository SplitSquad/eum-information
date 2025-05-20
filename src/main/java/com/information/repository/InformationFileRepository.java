package com.information.repository;

import com.information.entity.InformationFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InformationFileRepository extends JpaRepository<InformationFile, Long> {
    List<InformationFile> findByInformation_InformationId(long informationId);
}

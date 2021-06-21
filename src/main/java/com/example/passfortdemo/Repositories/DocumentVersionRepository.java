package com.example.passfortdemo.Repositories;

import com.example.passfortdemo.Entities.DocumentVersion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Time;
import java.util.List;
import java.util.Optional;

public interface DocumentVersionRepository extends JpaRepository<DocumentVersion, Integer> {
    List<DocumentVersion> findAllDocumentVersionByDocumentId(int documentId);
    Optional<DocumentVersion> findDocumentByIdAndTime(int documentId, Time time);
}

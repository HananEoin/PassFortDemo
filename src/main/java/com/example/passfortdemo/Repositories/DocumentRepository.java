package com.example.passfortdemo.Repositories;

import com.example.passfortdemo.Entities.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Integer> {
    List<Document> findAllDocumentsByTitle(String title);
}

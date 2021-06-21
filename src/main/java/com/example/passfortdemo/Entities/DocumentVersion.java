package com.example.passfortdemo.Entities;

import javax.persistence.*;
import java.sql.Time;

@Entity
@Table(name = "document_version_table")
public class DocumentVersion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int documentId;
    private int revisionNumber;
    private Time time;
    private String documentText;

    public DocumentVersion(){

    }

    public DocumentVersion(int documentId, int revisionNumber, Time time, String documentText) {
        this.documentId = documentId;
        this.revisionNumber = revisionNumber;
        this.time = time;
        this.documentText = documentText;
    }

    public int getDocumentId() {
        return documentId;
    }

    public void setDocumentId(int documentId) {
        this.documentId = documentId;
    }

    public int getRevisionNumber() {
        return revisionNumber;
    }

    public void setRevisionNumber(int revisionNumber) {
        this.revisionNumber = revisionNumber;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public String getDocumentText() {
        return documentText;
    }

    public void setDocumentText(String documentText) {
        this.documentText = documentText;
    }
}

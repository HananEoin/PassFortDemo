package com.example.passfortdemo.Entities;

import javax.persistence.*;

@Entity
@Table(name = "document_table")
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;

    public Document() {
    }

    public Document(String title) {
        this.title = title;
    }

    public Document(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public int getId(){
        return id;
    }

    public String getDocumentTitle(){
        return title;
    }

    public void setDocumentTitle(String documentTitle){
        this.title = documentTitle;
    }
}

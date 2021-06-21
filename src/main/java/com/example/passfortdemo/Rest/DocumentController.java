package com.example.passfortdemo.Rest;

import com.example.passfortdemo.Entities.Document;
import com.example.passfortdemo.Entities.DocumentVersion;
import com.example.passfortdemo.Repositories.DocumentRepository;
import com.example.passfortdemo.Repositories.DocumentVersionRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.slf4j.LoggerFactory.getLogger;

@RestController
@RequestMapping(value = "documents", produces = "application/json", consumes = "application/json")
public class DocumentController {
    private Logger logger = getLogger(DocumentController.class);

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private DocumentVersionRepository documentVersionRepository;


//    This should return a list of available titles.
    @GetMapping (value = "")
    public ResponseEntity getDocumentTitles(){
        List <String> titles= new ArrayList<>();

        List<Document> documents = documentRepository.findAll();

        for (int i =0; i < documents.size();i++){
            titles.add(documents.get(i).getDocumentTitle());
        }

        return  new ResponseEntity(titles, HttpStatus.OK);
    }

//    This should return a list of available revisions for a document.
    @GetMapping (value = "/{title}")
    public ResponseEntity getDocumentByTitle(@PathVariable final String title){
        List <String> revisions= new ArrayList<>();

        List<Document> documents = documentRepository.findAllDocumentsByTitle(title);

        if(documents.size() > 0){
            int documentId = documents.get(0).getId();

            List<DocumentVersion> documentVersions = documentVersionRepository.findAllDocumentVersionByDocumentId(documentId);

            for (int i =0; i < documentVersions.size();i++){
                revisions.add("Revision: " + documentVersions.get(i).getRevisionNumber() + " recorded at: " + documentVersions.get(i).getTime());
            }
            logger.debug("Returning revisions");
            return  new ResponseEntity(revisions, HttpStatus.OK);
        }

        logger.debug("Failed to find version");
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }


//    This should return the document as it was at that timestamp.
    @GetMapping (value = "/{title}/{timestamp}")
    public ResponseEntity getVersionByTimestamp(@PathVariable final String title, @PathVariable final Time timestamp){
        List <String> revisions= new ArrayList<>();

        List<Document> documents = documentRepository.findAllDocumentsByTitle(title);

        if(documents.size() > 0){
            int documentId = documents.get(0).getId();

            Optional<DocumentVersion> optionalDocumentVersion = documentVersionRepository.findDocumentByIdAndTime(documentId, timestamp);

            if(optionalDocumentVersion.isPresent()){
                logger.debug("Returning revision at timestamp");
                return  new ResponseEntity(optionalDocumentVersion.get(), HttpStatus.OK);
            }
            logger.debug("Could not find revision at given timestamp");
            return  new ResponseEntity(revisions, HttpStatus.NO_CONTENT);
        }

        logger.debug("Failed to find version");
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

//    This should return the current latest version of the document.
@GetMapping (value = "/{title}/latest")
public ResponseEntity getVersionByLatest(@PathVariable final String title){
    //Get Document ID from Title
    List<Document> documents = documentRepository.findAllDocumentsByTitle(title);

    if(documents.size() > 0){
        int documentId = documents.get(0).getId();

        //Get revisions from Document Id
        List<DocumentVersion> documentVersions = documentVersionRepository.findAllDocumentVersionByDocumentId(documentId);

        //Return document
        logger.debug("Latest Document Version found for with document with name: {}", title);
        return  new ResponseEntity(documentVersions.get(documentVersions.size() - 1), HttpStatus.OK);
    }
    logger.debug("Failed to find document");
    return new ResponseEntity(HttpStatus.BAD_REQUEST);
}

//    This allows users to post a new revision of a document.
//    It should receive JSON in the form: {content: ‘new content...’} .
    @PostMapping (value = "/<title>")
    public ResponseEntity postRevision(@PathVariable final String title, @RequestBody DocumentVersion documentVersion){
        logger.debug("Creating revision number {} relating to document with id {}", documentVersion.getRevisionNumber(), documentVersion.getDocumentId());
        documentVersionRepository.save(documentVersion);

        logger.debug("Successfully created revision version {}", documentVersion.getRevisionNumber());
        return new ResponseEntity(HttpStatus.CREATED);
    }

}

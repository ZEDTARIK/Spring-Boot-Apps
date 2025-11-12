package com.ettarak.resources;

import com.ettarak.entities.Contact;
import com.ettarak.services.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static com.ettarak.constant.Constant.PHOTO_DIRECTORY;
import static org.springframework.http.MediaType.*;

@RestController
@RequestMapping(path = "/contacts")
@RequiredArgsConstructor
public class ContactResource {
    private final ContactService contactService;

    @GetMapping

    public ResponseEntity<Map<String, Object>> getContacts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<Contact> contacts = contactService.getContacts(page, size);

        Map<String, Object> response = new HashMap<>();
        response.put("content", contacts.getContent());
        response.put("currentPage", contacts.getNumber());
        response.put("totalItems", contacts.getTotalElements());
        response.put("totalPages", contacts.getTotalPages());

        return ResponseEntity.ok(response);
    }


    @GetMapping(path = "/{id}")
    public ResponseEntity<Contact> getContactById(@PathVariable(value = "id") String id) {
                return ResponseEntity.ok().body(contactService.getContactById(id));
     }

    @PostMapping
    public ResponseEntity<Contact> saveContact(@RequestBody Contact contact) {
        return ResponseEntity
                .created(URI.create("/contacts/" + contact.getId()))
                .body(contactService.saveContact(contact));
    }

     @PutMapping(path = "/photo")
    public ResponseEntity<String> uploadPhoto(@RequestParam(value = "id") String id, @RequestParam(value = "file") MultipartFile file) {
                return ResponseEntity
                        .ok()
                        .body(contactService.uploadPhoto(id, file));
     }

    @GetMapping(path = "/image/{filename}")
    public ResponseEntity<byte[]> getImage(@PathVariable String filename) throws IOException {
        byte[] image = Files.readAllBytes(Paths.get(PHOTO_DIRECTORY, filename));
        String contentType = Files.probeContentType(Paths.get(PHOTO_DIRECTORY, filename));
        return ResponseEntity.ok()
                .header("Content-Type", contentType)
                .body(image);
    }



}

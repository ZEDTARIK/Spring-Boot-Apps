package com.ettarak.services;

import com.ettarak.entities.Contact;
import com.ettarak.exceptions.InvalidImageException;
import com.ettarak.repositories.ContactRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

import static com.ettarak.constant.Constant.PHOTO_DIRECTORY;

@Service
@Transactional(rollbackOn =  Exception.class)
@RequiredArgsConstructor
public class ContactService {
    private final ContactRepository contactRepository;

    public Page<Contact> getContacts(int page, int size) {
        return contactRepository.findAll(PageRequest.of(page, size  , Sort.by(Sort.Direction.ASC, "name")));
    }

    public Contact getContactById(String id) {
        return contactRepository.findById(id).orElseThrow(()-> new RuntimeException("contact not found"));
    }

    public Contact saveContact(Contact contact) {
        return contactRepository.save(contact);
    }

    public String uploadPhoto(String id, MultipartFile file) {
        // 🧠 Validate input file
        if (file == null || file.isEmpty() || file.getSize() == 0) {
            throw new InvalidImageException("Cannot upload an empty image (0 KB).");
        }


        Contact contact = getContactById(id);
        String photoUrl = photoFunction.apply(id, file);
        contact.setPhotoUrl(photoUrl);
        contactRepository.save(contact);
        return photoUrl;
    }



    private final Function<String, String> fileExtension = fileName ->
        Optional.of(fileName).filter(name -> name.contains("."))
                .map(name ->  "." + name.substring(name.lastIndexOf(".") + 1))
                .orElse(".png");

    private final BiFunction<String, MultipartFile, String> photoFunction = (id, image) -> {
        String fileName = id + fileExtension.apply(image.getOriginalFilename());
        try {
            Path fileStorageLocation = Paths.get(PHOTO_DIRECTORY).toAbsolutePath().normalize();
            if (Files.notExists(fileStorageLocation)) {
                Files.createDirectories(fileStorageLocation);
            }

            Files.copy(image.getInputStream(), fileStorageLocation.resolve(fileName),
                    StandardCopyOption.REPLACE_EXISTING);

            return ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/contacts/image/" + fileName)
                    .toUriString();

        } catch (Exception ex) {
            throw new RuntimeException("Unable to save image", ex);
        }
    };


}

package alexjuro.de.kleiderschrank.controller;

import alexjuro.de.kleiderschrank.dto.ClothingDTO;
import alexjuro.de.kleiderschrank.service.ClothingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clothing")
public class ClothingController {
    @Autowired
    ClothingService clothingService;

    @PostMapping(
            consumes = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE},
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE
            })
    public ResponseEntity<ClothingDTO> createClothing(@RequestBody ClothingDTO clothingDTO) throws Exception {
        clothingService.saveClothing(clothingDTO);
        return new ResponseEntity<>(clothingDTO, HttpStatus.CREATED);
    }

    // patch picture
    // patch information
    // delete clothing
}

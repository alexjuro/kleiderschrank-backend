package alexjuro.de.kleiderschrank.controller;

import alexjuro.de.kleiderschrank.dto.ClothingDTO;
import alexjuro.de.kleiderschrank.dto.ClothingInLaundryDTO;
import alexjuro.de.kleiderschrank.service.ClothingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<ClothingDTO> createClothing(@RequestBody ClothingDTO clothingDTO) {
        clothingService.saveClothing(clothingDTO);
        return new ResponseEntity<>(clothingDTO, HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<ClothingDTO>> getAllClothings(@RequestParam Integer closetId) {
        List<ClothingDTO> clothingDTOs = clothingService.getAllClothings(closetId);
        return new ResponseEntity<>(clothingDTOs, HttpStatus.OK);
    }

    @PatchMapping(
            consumes = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE},
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE
            })
    public ResponseEntity<ClothingDTO> updateClothing(@RequestBody ClothingDTO clothingDTO) {
        try{
            clothingService.updateClothing(clothingDTO);
            return new ResponseEntity<>(clothingDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(clothingDTO, HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping(
            value = "/inLaundry",
            consumes = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE},
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE
            })
    public ResponseEntity<Boolean> updateLaundryStatus(@RequestBody ClothingInLaundryDTO clothingInLaundryDTO) {
        try {
            clothingService.updateLaundryStatus(clothingInLaundryDTO);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping()
    public ResponseEntity<Integer> deleteClothing(@RequestParam Integer id) {
        try{
            clothingService.deleteClothing(id);
            return new ResponseEntity<>(id, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
package alexjuro.de.kleiderschrank.controller;

import alexjuro.de.kleiderschrank.dto.OufitRequestDTO;
import alexjuro.de.kleiderschrank.dto.OutfitAddDTO;
import alexjuro.de.kleiderschrank.dto.OutfitPatchDTO;
import alexjuro.de.kleiderschrank.dto.OutfitWithLaundryDTO;
import alexjuro.de.kleiderschrank.service.OutfitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/outfit")
public class OutfitController {
    @Autowired
    private OutfitService outfitService;

    @PostMapping(
            consumes = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE},
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE
            })
    public ResponseEntity<String> createOutfit(@RequestBody OutfitAddDTO outfitAddDTO) {
        try{
            outfitService.createOutfit(outfitAddDTO);
            return new ResponseEntity<>("Created", HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping()
    public ResponseEntity<List<OutfitWithLaundryDTO>> getOutfits(@RequestBody OufitRequestDTO outfitRequestDTO) {
        try{
            if(outfitRequestDTO.getInLaundry() == null){
                List<OutfitWithLaundryDTO> res = outfitService.getOutfitsInLaundry(null);
                return new ResponseEntity<>(res, HttpStatus.OK);
            } else if (outfitRequestDTO.getInLaundry()) {
                List<OutfitWithLaundryDTO> res = outfitService.getOutfitsInLaundry(Boolean.TRUE);
                return new ResponseEntity<>(res, HttpStatus.OK);
            } else {
                List<OutfitWithLaundryDTO> res = outfitService.getOutfitsInLaundry(Boolean.FALSE);
                return new ResponseEntity<>(res, HttpStatus.OK);
            }
        } catch(Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/single")
    public ResponseEntity<OutfitWithLaundryDTO> getSingleOutfit(@RequestParam Integer outfitId) {
        try {
            OutfitWithLaundryDTO res = outfitService.getSingleOutfit(outfitId);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping(
            consumes = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE},
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE
            })
    public ResponseEntity<String> updateOutfit(@RequestBody OutfitPatchDTO outfitPatchDTO) {
        try {
            outfitService.patchOutfit(outfitPatchDTO);
            return new ResponseEntity<>("Updated", HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping()
    public ResponseEntity<String> deleteOutfit(@RequestParam Integer id) {
        try {
            outfitService.deleteOutfit(id);
            return new ResponseEntity<>("Deleted", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
        }
    }
}

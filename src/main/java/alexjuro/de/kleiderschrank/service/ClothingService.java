package alexjuro.de.kleiderschrank.service;

import alexjuro.de.kleiderschrank.domain.Closet;
import alexjuro.de.kleiderschrank.domain.Clothing;
import alexjuro.de.kleiderschrank.dto.ClothingDTO;
import alexjuro.de.kleiderschrank.dto.ClothingInLaundryDTO;
import alexjuro.de.kleiderschrank.repository.ClosetRepository;
import alexjuro.de.kleiderschrank.repository.ClothingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClothingService {
    @Autowired
    ClothingRepository clothingRepository;
    @Autowired
    ClosetRepository closetRepository;

    public void saveClothing(ClothingDTO clothingDTO) {
        Optional<Closet> closet = closetRepository.findById(1);

        if (closet.isPresent()) {
            Clothing clothing = Clothing
                    .builder()
                    .brand(clothingDTO.getBrand())
                    .description(clothingDTO.getDescription())
                    .imageBase64(clothingDTO.getImageBase64())
                    .inLaundry(clothingDTO.isInLaundry())
                    .category(clothingDTO.getCategory())
                    .color(clothingDTO.getColor())
                    .type(clothingDTO.getType())
                    .closet(closet.get())
                    .build();
            clothingRepository.save(clothing);
        }
    }

    public List<ClothingDTO> getAllClothings(Integer closetId) {
        List<Clothing> clothingList = clothingRepository.findByClosetId(closetId);
        List<ClothingDTO> clothingDTOList = new ArrayList<>();
        clothingList.forEach(clothing -> {
            clothingDTOList.add(ClothingDTO
                            .builder()
                            .id(clothing.getId())
                            .brand(clothing.getBrand())
                            .description(clothing.getDescription())
                            .imageBase64(clothing.getImageBase64())
                            .inLaundry(clothing.isInLaundry())
                            .category(clothing.getCategory())
                            .color(clothing.getColor())
                            .type(clothing.getType())
//                            .closet(clothing.getCloset())
//                            .outfits(clothing.getOutfits())
                            .build());
        });
        return clothingDTOList;
    }

    public ClothingDTO updateClothing(ClothingDTO clothingDTO) throws Exception {
        Optional<Clothing> clothing = clothingRepository.findById(clothingDTO.getId());
        if (clothing.isPresent()) {
            Clothing clo = clothing.get();

            clo.setBrand(clothingDTO.getBrand());
            clo.setDescription(clothingDTO.getDescription());
            clo.setImageBase64(clothingDTO.getImageBase64());
            clo.setCategory(clothingDTO.getCategory());
            clo.setColor(clothingDTO.getColor());
            clo.setType(clothingDTO.getType());

            clothingRepository.save(clo);
            return clothingDTO;
        } else {
            throw new Exception("No Clothing with given id found");
        }
    }

    public void updateLaundryStatus(ClothingInLaundryDTO clothingInLaundryDTO) throws Exception {
        Optional<Clothing> clothing = clothingRepository.findById(clothingInLaundryDTO.getId());
        if (clothing.isPresent()) {
            Clothing clo = clothing.get();

            clo.setInLaundry(clothingInLaundryDTO.isInLaundry());
            clothingRepository.save(clo);
        } else {
            throw new Exception("No Clothing with given id found");
        }
    }

    public void deleteClothing(Integer clothingId) {
        clothingRepository.deleteById(clothingId);
    }
}

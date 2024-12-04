package alexjuro.de.kleiderschrank.service;

import alexjuro.de.kleiderschrank.domain.Closet;
import alexjuro.de.kleiderschrank.domain.Clothing;
import alexjuro.de.kleiderschrank.dto.ClothingDTO;
import alexjuro.de.kleiderschrank.repository.ClosetRepository;
import alexjuro.de.kleiderschrank.repository.ClothingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

//    public ClothingDTO getClothingById(Integer id) {
//        Clothing clothing = clothingRepository.findById(id).orElse(null);
//        return clothing;
//    }
}

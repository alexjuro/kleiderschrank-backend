package alexjuro.de.kleiderschrank.service;

import alexjuro.de.kleiderschrank.domain.Closet;
import alexjuro.de.kleiderschrank.domain.Clothing;
import alexjuro.de.kleiderschrank.domain.Outfit;
import alexjuro.de.kleiderschrank.dto.OutfitAddDTO;
import alexjuro.de.kleiderschrank.dto.OutfitPatchDTO;
import alexjuro.de.kleiderschrank.dto.OutfitWithLaundryDTO;
import alexjuro.de.kleiderschrank.repository.ClosetRepository;
import alexjuro.de.kleiderschrank.repository.ClothingRepository;
import alexjuro.de.kleiderschrank.repository.OutfitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class OutfitService {
    @Autowired
    OutfitRepository outfitRepository;
    @Autowired
    ClosetRepository closetRepository;
    @Autowired
    ClothingRepository clothingRepository;

    public void createOutfit(OutfitAddDTO outfitAddDTO) {
        Optional<Closet> closetOptional = closetRepository.findById(outfitAddDTO.getClosetId());

        ArrayList<Clothing> clothingList = new ArrayList<>();
        outfitAddDTO.getClothingIdList().forEach(id -> {
            Optional<Clothing> clothingOptional = clothingRepository.findById(id);
            clothingOptional.ifPresent(clothingList::add);
        });

        if (closetOptional.isPresent()) {
            Closet closet = closetOptional.get();
            Outfit outfit = Outfit
                    .builder()
                    .closet(closet)
                    .clothings(clothingList)
                    .build();
            outfitRepository.save(outfit);
        }
    }

    public List<OutfitWithLaundryDTO> getOutfitsInLaundry(Integer closetId, Boolean inLaundry){
        List<Outfit> outfitList = outfitRepository.findByClosetId(closetId);
        ArrayList<OutfitWithLaundryDTO> outfitWithLaundryDTOs = new ArrayList<>();

        outfitList.forEach(outfit -> {
            AtomicInteger inLaundrysTrue = new AtomicInteger();
            outfit.getClothings().forEach(clo -> {
                if(clo.isInLaundry()) {
                    inLaundrysTrue.getAndIncrement();
                }
            });

            outfitWithLaundryDTOs.add(OutfitWithLaundryDTO
                    .builder()
                    .id(outfit.getId())
                    .inLaundry(inLaundrysTrue.get() == outfit.getClothings().size())
//                    .clothings(outfit.getClothings())
                    .build());
        });
        if(inLaundry == null){
            return outfitWithLaundryDTOs;
        } else if(inLaundry){
            return outfitWithLaundryDTOs.stream()
                    .filter(outfit -> Boolean.TRUE.equals(outfit.getInLaundry()))
                    .collect(Collectors.toList());
        } else {
            return outfitWithLaundryDTOs.stream()
                    .filter(outfit -> Boolean.FALSE.equals(outfit.getInLaundry()))
                    .collect(Collectors.toList());
        }
    }

    public OutfitWithLaundryDTO getSingleOutfit(Integer outfitId) throws Exception{
        Optional<Outfit> outfitOptional = outfitRepository.findById(outfitId);
        outfitOptional.ifPresent(outfit -> {
             OutfitWithLaundryDTO
                .builder()
                .build();
        });
        if(outfitOptional.isPresent()){
            Outfit outfit = outfitOptional.get();
            AtomicInteger count = new AtomicInteger();
            outfit.getClothings().forEach(clo -> {
                if(clo.isInLaundry()) {
                    count.getAndIncrement();
                };
            });

            return OutfitWithLaundryDTO
                    .builder()
                    .id(outfitId)
                    .closet(outfit.getCloset())
//                    .clothings(outfit.getClothings())
                    .inLaundry(outfit.getClothings().size() == count.get())
                    .build();
        } else {
            throw new Exception("Not Found");
        }
    }

    public void patchOutfit(OutfitPatchDTO outfitPatchDTO) throws Exception{
        Optional<Outfit> outfit  = outfitRepository.findById(outfitPatchDTO.getId());
        if(outfit.isPresent()) {
            Outfit out = outfit.get();
            ArrayList<Clothing> clothing = new ArrayList<>();

            outfitPatchDTO.getClothingIdList().forEach(id -> {
                Optional<Clothing> clothingOptional = clothingRepository.findById(id);
                clothingOptional.ifPresent(clothing::add);
            });

            if(clothing.size() != outfitPatchDTO.getClothingIdList().size()) {
                throw new Exception("Not all clothings added");
            } else {
                out.setClothings(clothing);
                outfitRepository.save(out);
            }
        }
    }

    public void deleteOutfit(Integer id) {
        outfitRepository.deleteById(id);
    }
}

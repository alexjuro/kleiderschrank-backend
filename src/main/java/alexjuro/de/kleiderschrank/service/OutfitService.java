package alexjuro.de.kleiderschrank.service;

import alexjuro.de.kleiderschrank.domain.Closet;
import alexjuro.de.kleiderschrank.domain.Clothing;
import alexjuro.de.kleiderschrank.domain.Outfit;
import alexjuro.de.kleiderschrank.dto.*;
import alexjuro.de.kleiderschrank.exceptions.NotAuthenticatedException;
import alexjuro.de.kleiderschrank.repository.ClosetRepository;
import alexjuro.de.kleiderschrank.repository.ClothingRepository;
import alexjuro.de.kleiderschrank.repository.OutfitRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OutfitService {
    @Autowired
    OutfitRepository outfitRepository;
    @Autowired
    ClosetRepository closetRepository;
    @Autowired
    ClothingRepository clothingRepository;

    public void createOutfit(OutfitAddDTO outfitAddDTO) throws Exception {
        log.info("creating new outfit");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            UserDTO u = (UserDTO) authentication.getPrincipal();
            Optional<Closet> closetOptional  = closetRepository.findById(u.getCloset().getId());

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
        } else {
            log.info("user is not authenticated");
            throw new NotAuthenticatedException("The user is not authenticated");
        }
    }

    public List<OutfitWithLaundryDTO> getOutfitsInLaundry(Boolean inLaundry) throws Exception {
        log.info("getting all outfits of user");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            UserDTO u = (UserDTO) authentication.getPrincipal();

            List<Outfit> outfitList = outfitRepository.findByClosetId(u.getCloset().getId());
            ArrayList<OutfitWithLaundryDTO> outfitWithLaundryDTOs = new ArrayList<>();

            outfitList.forEach(outfit -> {
                AtomicInteger inLaundrysTrue = new AtomicInteger();
                List<ClothingDTO> clothingDTOList = new ArrayList<>();
                outfit.getClothings().forEach(clo -> {
                    if(clo.isInLaundry()) {
                        inLaundrysTrue.getAndIncrement();
                    }
                    clothingDTOList.add(
                            ClothingDTO
                                    .builder()
                                    .id(clo.getId())
                                    .brand(clo.getBrand())
                                    .description(clo.getDescription())
                                    .imageBase64(clo.getImageBase64())
                                    .category(clo.getCategory())
                                    .color(clo.getColor())
                                    .type(clo.getType())
                                    .build()
                    );
                });

                outfitWithLaundryDTOs.add(OutfitWithLaundryDTO
                        .builder()
                        .id(outfit.getId())
                        .inLaundry(inLaundrysTrue.get() == outfit.getClothings().size())
                        .clothingDTOList(clothingDTOList)
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

        } else {
            log.info("user is not authenticated");
            throw new NotAuthenticatedException("The user is not authenticated");
        }
    }

    public OutfitWithLaundryDTO getSingleOutfit(Integer outfitId) throws Exception{
        log.info("getting one specific outfit");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            UserDTO u = (UserDTO) authentication.getPrincipal();

            Optional<Outfit> outfitOptional = outfitRepository.findByClosetIdAndId(u.getCloset().getId(), outfitId);
            outfitOptional.ifPresent(outfit -> {
                 OutfitWithLaundryDTO
                    .builder()
                    .build();
            });
            if(outfitOptional.isPresent()){
                Outfit outfit = outfitOptional.get();
                AtomicInteger count = new AtomicInteger();
                List<ClothingDTO> clothingDTOList = new ArrayList<>();
                outfit.getClothings().forEach(clo -> {
                    if(clo.isInLaundry()) {
                        count.getAndIncrement();
                    };

                    clothingDTOList.add(
                            ClothingDTO
                                    .builder()
                                    .id(clo.getId())
                                    .brand(clo.getBrand())
                                    .description(clo.getDescription())
                                    .imageBase64(clo.getImageBase64())
                                    .category(clo.getCategory())
                                    .color(clo.getColor())
                                    .type(clo.getType())
                                    .build()
                    );
                });

                return OutfitWithLaundryDTO
                        .builder()
                        .id(outfitId)
                        .clothingDTOList(clothingDTOList)
                        .inLaundry(outfit.getClothings().size() == count.get())
                        .build();
            } else {
                throw new Exception("Not Found");
            }
        } else {
            log.info("user is not authenticated");
            throw new NotAuthenticatedException("The user is not authenticated");
        }
    }

    public void patchOutfit(OutfitPatchDTO outfitPatchDTO) throws Exception{
        log.info("patching outfit with id {}", outfitPatchDTO.getId());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            UserDTO u = (UserDTO) authentication.getPrincipal();
            Optional<Outfit> outfitOptional = outfitRepository.findByClosetIdAndId(u.getCloset().getId(), outfitPatchDTO.getId());

            if(outfitOptional.isPresent()) {
                Outfit out = outfitOptional.get();
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
            } else {
                log.info("user tried to update not own outfit");
                throw new Exception("Not own outfit");
            }
        } else {
            log.info("user is not authenticated");
            throw new NotAuthenticatedException("The user is not authenticated");
        }
    }

    public void deleteOutfit(Integer id) throws Exception{
        log.info("deleting ouftit with id {}", id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            UserDTO u = (UserDTO) authentication.getPrincipal();
            Optional<Outfit> outfitOptional = outfitRepository.findByClosetIdAndId(u.getCloset().getId(), id);

            if(outfitOptional.isPresent()) {
                outfitRepository.deleteById(id);
            } else {
                log.info("user tried to delete not own outfit");
                throw new Exception("Not own outfit");
            }
        } else {
            log.info("user is not authenticated");
            throw new NotAuthenticatedException("The user is not authenticated");
        }
    }
}

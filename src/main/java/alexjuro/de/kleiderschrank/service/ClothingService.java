package alexjuro.de.kleiderschrank.service;

import alexjuro.de.kleiderschrank.domain.Closet;
import alexjuro.de.kleiderschrank.domain.Clothing;
import alexjuro.de.kleiderschrank.dto.ClothingDTO;
import alexjuro.de.kleiderschrank.dto.ClothingInLaundryDTO;
import alexjuro.de.kleiderschrank.dto.OutfitDTO;
import alexjuro.de.kleiderschrank.dto.UserDTO;
import alexjuro.de.kleiderschrank.exceptions.NotAuthenticatedException;
import alexjuro.de.kleiderschrank.repository.ClosetRepository;
import alexjuro.de.kleiderschrank.repository.ClothingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class ClothingService {
    @Autowired
    ClothingRepository clothingRepository;
    @Autowired
    ClosetRepository closetRepository;





//    log.info("");
//    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//    if (authentication != null && authentication.isAuthenticated()) {
//        UserDTO u = (UserDTO) authentication.getPrincipal();
//
//        return ;
//    } else {
//        log.info("user is not authenticated");
//        throw new NotAuthenticatedException("The user is not authenticated");
//    }







    public void saveClothing(ClothingDTO clothingDTO) throws Exception {
        log.info("saving Clothing");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            UserDTO u = (UserDTO) authentication.getPrincipal();

            Optional<Closet> closet = closetRepository.findById(u.getCloset().getId());

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
        } else {
            log.info("user is not authenticated");
            throw new NotAuthenticatedException("The user is not authenticated");
        }
    }

    public List<ClothingDTO> getAllClothings() throws Exception{
        log.info("getting all Clothings");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            UserDTO u = (UserDTO) authentication.getPrincipal();

            List<Clothing> clothingList = clothingRepository.findByClosetId(u.getCloset().getId());
            List<ClothingDTO> clothingDTOList = new ArrayList<>();
            clothingList.forEach(clothing -> {
                List<OutfitDTO> outfitDTOList = new ArrayList<>();
                clothing.getOutfits().forEach(outfit -> {
                    outfitDTOList.add(
                            OutfitDTO
                                    .builder()
                                    .id(outfit.getId())
                                    .build()
                    );
                });

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
//                      .closet(clothing.getCloset())
                        .outfitDTOs(outfitDTOList)
                        .build());
            });
            return clothingDTOList;
        } else {
            log.info("user is not authenticated");
            throw new NotAuthenticatedException("The user is not authenticated");
        }
    }

    public ClothingDTO updateClothing(ClothingDTO clothingDTO) throws Exception {
        log.info("updating clothing");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            UserDTO u = (UserDTO) authentication.getPrincipal();
            Optional<Closet> closet = closetRepository.findById(u.getCloset().getId());
            Optional<Clothing> clothing = clothingRepository.findById(clothingDTO.getId());

            if (clothing.isPresent() && closet.isPresent()) {
                if (!Objects.equals(clothing.get().getCloset().getId(), closet.get().getId())) {
                    log.info("clothing not part of own closet");
                    throw new Exception("not own closet");
                }
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
        } else {
            log.info("user is not authenticated");
            throw new NotAuthenticatedException("The user is not authenticated");
        }
    }

    public void updateLaundryStatus(ClothingInLaundryDTO clothingInLaundryDTO) throws Exception {
        log.info("updating clothing laundry status");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            UserDTO u = (UserDTO) authentication.getPrincipal();
            Optional<Closet> closet = closetRepository.findById(u.getCloset().getId());
            Optional<Clothing> clothing = clothingRepository.findById(clothingInLaundryDTO.getId());

            if (clothing.isPresent() && closet.isPresent()) {
                if (!Objects.equals(clothing.get().getCloset().getId(), closet.get().getId())) {
                    log.info("clothing not part of own closet");
                    throw new Exception("not own closet");
                }
                Clothing clo = clothing.get();

                clo.setInLaundry(clothingInLaundryDTO.isInLaundry());
                clothingRepository.save(clo);
            } else {
                throw new Exception("No Clothing with given id found");
            }
        } else {
            log.info("user is not authenticated");
            throw new NotAuthenticatedException("The user is not authenticated");
        }
    }

    public void deleteClothing(Integer clothingId) throws Exception {
        log.info("deleting clothing");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            UserDTO u = (UserDTO) authentication.getPrincipal();
            Optional<Closet> closet = closetRepository.findById(u.getCloset().getId());
            Optional<Clothing> clothing = clothingRepository.findById(clothingId);

            if (clothing.isPresent() && closet.isPresent()) {
                if (!Objects.equals(clothing.get().getCloset().getId(), closet.get().getId())) {
                    log.info("clothing not part of own closet");
                    throw new Exception("not own closet");
                }
                clothingRepository.deleteById(clothingId);
            } else {
                throw new Exception("No Clothing with given id found");
            }
        } else {
            log.info("user is not authenticated");
            throw new NotAuthenticatedException("The user is not authenticated");
        }
    }
}

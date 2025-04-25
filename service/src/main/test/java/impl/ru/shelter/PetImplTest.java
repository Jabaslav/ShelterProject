package impl.ru.shelter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import ru.shelter.dto.request.PetCreateRequestDto;
import ru.shelter.dto.response.PetResponseDto;
import ru.shelter.impl.ImageStorageImpl;
import ru.shelter.impl.PetImpl;
import ru.shelter.interfaces.PetDao;
import ru.shelter.mapper.PetMapper;
import ru.shelter.model.Pet;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PetImplTest {

    @Mock
    private PetDao petDao;

    @Mock
    private PetMapper petMapper;

    @Mock
    private ImageStorageImpl imageStorage;

    @InjectMocks
    private PetImpl petService;

    private final String testImagePath = "/uploads/test_pet.jpg";

    private PetCreateRequestDto createTestRequestDto(MultipartFile image) {
        return new PetCreateRequestDto(
                "Test Pet",
                "Dog",
                "Friendly test pet"
        );
    }

    private Pet createTestPet(Long id, String imageAddress) {
        Pet pet = new Pet();
        pet.setId(id);
        pet.setName("Test Pet");
        pet.setImageAddress(imageAddress);
        pet.setType("Dog");
        pet.setDescription("Friendly test pet");
        return pet;
    }

    private PetResponseDto createTestResponseDto(Long id, String imageAddress) {
        return new PetResponseDto(
                id,
                "Test Pet",
                imageAddress,
                "Dog",
                "Friendly test pet"
        );
    }

    @Test
    void add_ShouldReturnPetResponse_WhenSuccess() {
        // Arrange
        MultipartFile image = new MockMultipartFile(
                "image",
                "test_pet.jpg",
                "image/jpeg",
                "test image".getBytes()
        );
        PetCreateRequestDto requestDto = createTestRequestDto(image);
        Pet pet = createTestPet(null, null);
        Pet savedPet = createTestPet(1L, testImagePath);
        PetResponseDto expectedResponse = createTestResponseDto(1L, testImagePath);

        when(petMapper.fromDto(requestDto)).thenReturn(pet);
        when(imageStorage.saveImage(image)).thenReturn(testImagePath);
        when(petDao.save(pet)).thenReturn(savedPet);
        when(petMapper.toPetResponse(savedPet)).thenReturn(expectedResponse);

        // Act
        PetResponseDto actualResponse = petService.addWithImage(requestDto, image);

        // Assert
        assertEquals(expectedResponse.id(), actualResponse.id());
        assertEquals(expectedResponse.imageAddress(), actualResponse.imageAddress());
        verify(petDao).save(pet);
        verify(imageStorage).saveImage(image);
    }

    @Test
    void addWithImage_ShouldNotSaveImage_WhenImageIsNull() {
        // Arrange
        PetCreateRequestDto requestDto = createTestRequestDto(null);
        Pet pet = createTestPet(null, null);
        Pet savedPet = createTestPet(1L, null);
        PetResponseDto expectedResponse = createTestResponseDto(1L, null);

        when(petMapper.fromDto(requestDto)).thenReturn(pet);
        when(petDao.save(pet)).thenReturn(savedPet);
        when(petMapper.toPetResponse(savedPet)).thenReturn(expectedResponse);

        // Act
        PetResponseDto actualResponse = petService.addWithImage(requestDto, null);

        // Assert

        assertNull(actualResponse.imageAddress());
        verify(imageStorage, never()).saveImage(any());
    }

    @Test
    void get_ShouldReturnPetResponse_WhenPetExists() {
        // Arrange
        Long petId = 1L;
        Pet pet = createTestPet(petId, testImagePath);
        PetResponseDto expectedResponse = createTestResponseDto(petId, testImagePath);

        when(petDao.findById(petId)).thenReturn(Optional.of(pet));
        when(petMapper.toPetResponse(pet)).thenReturn(expectedResponse);

        // Act
        Optional<PetResponseDto> actualResponse = petService.get(petId);

        // Assert
        assertTrue(actualResponse.isPresent());
        assertEquals(expectedResponse, actualResponse.get());
    }

    @Test
    void updateWithImage_ShouldUpdateImage_WhenNewImageProvided() {
        // Arrange
        Long petId = 1L;
        MultipartFile newImage = new MockMultipartFile(
                "newImage",
                "new_image.jpg",
                "image/jpeg",
                "new image".getBytes()
        );
        PetCreateRequestDto requestDto = createTestRequestDto(newImage);
        Pet existingPet = createTestPet(petId, "/uploads/old_image.jpg");
        Pet updatedPet = createTestPet(petId, testImagePath);
        PetResponseDto expectedResponse = createTestResponseDto(petId, testImagePath);

        when(petDao.findById(petId)).thenReturn(Optional.of(existingPet));
        when(petMapper.fromDto(requestDto)).thenReturn(updatedPet);
        when(imageStorage.saveImage(newImage)).thenReturn(testImagePath);
        when(petDao.save(updatedPet)).thenReturn(updatedPet);
        when(petMapper.toPetResponse(updatedPet)).thenReturn(expectedResponse);

        // Act
        PetResponseDto actualResponse = petService.updateWithImage(requestDto, petId, newImage);

        // Assert
        assertNotNull(actualResponse);
        assertEquals(testImagePath, actualResponse.imageAddress());
        verify(imageStorage).saveImage(newImage);
    }

    @Test
    void update_ShouldKeepExistingImage_WhenNoNewImageProvided() {
        // Arrange
        Long petId = 1L;
        String existingImagePath = "/uploads/existing.jpg";
        PetCreateRequestDto requestDto = createTestRequestDto(null);
        Pet existingPet = createTestPet(petId, existingImagePath);
        Pet updatedPet = createTestPet(petId, existingImagePath);
        PetResponseDto expectedResponse = createTestResponseDto(petId, existingImagePath);

        when(petDao.findById(petId)).thenReturn(Optional.of(existingPet));
        when(petMapper.fromDto(requestDto)).thenReturn(updatedPet);
        when(petDao.save(updatedPet)).thenReturn(updatedPet);
        when(petMapper.toPetResponse(updatedPet)).thenReturn(expectedResponse);

        // Act
        PetResponseDto actualResponse = petService.updateWithImage(requestDto, petId, null);

        // Assert
        assertNotNull(actualResponse);
        assertEquals(existingImagePath, actualResponse.imageAddress());
        verify(imageStorage, never()).saveImage(any());
    }

    @Test
    void remove_ShouldReturnTrue_WhenPetExists() {
        // Arrange
        Long petId = 1L;
        doNothing().when(petDao).deleteById(petId);

        // Act
        boolean result = petService.remove(petId);

        // Assert
        assertTrue(result);
        verify(petDao).deleteById(petId);
    }

    @Test
    void getAll_ShouldReturnListOfPets() {
        // Arrange
        Pet pet1 = createTestPet(1L, "/uploads/pet1.jpg");
        Pet pet2 = createTestPet(2L, "/uploads/pet2.jpg");
        List<Pet> pets = List.of(pet1, pet2);
        PetResponseDto response1 = createTestResponseDto(1L, "/uploads/pet1.jpg");
        PetResponseDto response2 = createTestResponseDto(2L, "/uploads/pet2.jpg");
        List<PetResponseDto> expected = List.of(response1, response2);

        when(petDao.findAll()).thenReturn(pets);
        when(petMapper.toPetResponseList(pets)).thenReturn(expected);

        // Act
        List<PetResponseDto> result = petService.getAll();

        // Assert
        assertEquals(2, result.size());
        assertEquals(expected, result);
    }

    @Test
    void getAll_ShouldReturnEmptyList_WhenNoPetsExist() {
        // Arrange
        when(petDao.findAll()).thenReturn(Collections.emptyList());
        when(petMapper.toPetResponseList(Collections.emptyList()))
                .thenReturn(Collections.emptyList());

        // Act
        List<PetResponseDto> result = petService.getAll();

        // Assert
        assertTrue(result.isEmpty());
    }
}
package java.impl.ru.shelter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import ru.shelter.dto.request.PostCreateRequestDto;
import ru.shelter.dto.response.PostResponseDto;
import ru.shelter.impl.ImageStorageImpl;
import ru.shelter.impl.PostImpl;
import ru.shelter.interfaces.PostDao;
import ru.shelter.mapper.PostMapper;
import ru.shelter.model.Post;
import ru.shelter.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostImplTest {

    @Mock
    private PostDao postDao;

    @Mock
    private PostMapper postMapper;

    @Mock
    private ImageStorageImpl imageStorage;

    @InjectMocks
    private PostImpl postService;

    private final LocalDateTime testCreationTime = LocalDateTime.now();
    private final String testImagePath = "/uploads/test_post.jpg";

    private PostCreateRequestDto createTestRequestDto() {
        return new PostCreateRequestDto(
                1L, // authorId
                "Test post description"
        );
    }

    private Post createTestPost(Long id, boolean withImage) {
        User author = new User();
        author.setId(1L);

        Post post = new Post();
        post.setId(id);
        post.setUser(author);
        post.setDescription("Test post description");
        post.setCreationTime(testCreationTime);
        if (withImage) {
            post.setImageAddress(testImagePath);
        }
        return post;
    }

    private PostResponseDto createTestResponseDto(Long id, boolean withImage) {
        return new PostResponseDto(
                id,
                1L, // authorId
                withImage ? testImagePath : null,
                "Test post description",
                testCreationTime
        );
    }

    @Test
    void add_ShouldReturnPostResponse_WhenSuccess() {
        // Arrange
        PostCreateRequestDto requestDto = createTestRequestDto();
        Post post = createTestPost(null, false);
        Post savedPost = createTestPost(1L, false);
        PostResponseDto expectedResponse = createTestResponseDto(1L, false);

        when(postMapper.fromDto(requestDto)).thenReturn(post);
        when(postDao.save(post)).thenReturn(savedPost);
        when(postMapper.toPostResponse(savedPost)).thenReturn(expectedResponse);

        // Act
        PostResponseDto actualResponse = postService.add(requestDto);

        // Assert
        assertNotNull(actualResponse);
        assertEquals(expectedResponse, actualResponse);
        verify(postDao).save(post);
    }

    @Test
    void addWithImage_ShouldReturnPostResponseWithImage_WhenSuccess() {
        // Arrange
        PostCreateRequestDto requestDto = createTestRequestDto();
        MultipartFile image = new MockMultipartFile(
                "image",
                "test_post.jpg",
                "image/jpeg",
                "test image content".getBytes()
        );

        Post post = createTestPost(null, false);
        Post savedPost = createTestPost(1L, true);
        PostResponseDto expectedResponse = createTestResponseDto(1L, true);

        when(postMapper.fromDto(requestDto)).thenReturn(post);
        when(imageStorage.saveImage(image)).thenReturn(testImagePath);
        when(postDao.save(post)).thenReturn(savedPost);
        when(postMapper.toPostResponse(savedPost)).thenReturn(expectedResponse);

        // Act
        PostResponseDto actualResponse = postService.addWithImage(requestDto, image);

        // Assert
        assertNotNull(actualResponse);
        assertEquals(testImagePath, actualResponse.pictureAddress());
        verify(imageStorage).saveImage(image);
    }

    @Test
    void get_ShouldReturnPostResponse_WhenPostExists() {
        // Arrange
        Long postId = 1L;
        Post post = createTestPost(postId, true);
        PostResponseDto expectedResponse = createTestResponseDto(postId, true);

        when(postDao.findById(postId)).thenReturn(Optional.of(post));
        when(postMapper.toPostResponse(post)).thenReturn(expectedResponse);

        // Act
        Optional<PostResponseDto> actualResponse = postService.get(postId);

        // Assert
        assertTrue(actualResponse.isPresent());
        assertEquals(expectedResponse, actualResponse.get());
    }

    @Test
    void update_ShouldReturnUpdatedPost_WhenPostExists() {
        // Arrange
        Long postId = 1L;
        PostCreateRequestDto requestDto = new PostCreateRequestDto(
                1L,
                "Updated description"
        );

        Post existingPost = createTestPost(postId, false);
        Post updatedPost = createTestPost(postId, false);
        updatedPost.setDescription("Updated description");

        PostResponseDto expectedResponse = new PostResponseDto(
                postId,
                1L,
                null,
                "Updated description",
                testCreationTime
        );

        when(postDao.findById(postId)).thenReturn(Optional.of(existingPost));
        when(postMapper.fromDto(requestDto)).thenReturn(updatedPost);
        when(postDao.save(updatedPost)).thenReturn(updatedPost);
        when(postMapper.toPostResponse(updatedPost)).thenReturn(expectedResponse);

        // Act
        PostResponseDto actualResponse = postService.update(requestDto, postId);

        // Assert
        assertNotNull(actualResponse);
        assertEquals("Updated description", actualResponse.description());
    }

    @Test
    void updateWithImage_ShouldUpdateImage_WhenNewImageProvided() {
        // Arrange
        Long postId = 1L;
        PostCreateRequestDto requestDto = createTestRequestDto();
        MultipartFile newImage = new MockMultipartFile(
                "newImage",
                "new_image.jpg",
                "image/jpeg",
                "new image content".getBytes()
        );

        Post existingPost = createTestPost(postId, false);
        Post updatedPost = createTestPost(postId, true);
        PostResponseDto expectedResponse = createTestResponseDto(postId, true);

        when(postDao.findById(postId)).thenReturn(Optional.of(existingPost));
        when(postMapper.fromDto(requestDto)).thenReturn(updatedPost);
        when(imageStorage.saveImage(newImage)).thenReturn(testImagePath);
        when(postDao.save(updatedPost)).thenReturn(updatedPost);
        when(postMapper.toPostResponse(updatedPost)).thenReturn(expectedResponse);

        // Act
        PostResponseDto actualResponse = postService.updateWithImage(requestDto, postId, newImage);

        // Assert
        assertNotNull(actualResponse);
        assertEquals(testImagePath, actualResponse.pictureAddress());
    }

    @Test
    void remove_ShouldReturnTrue_WhenPostExists() {
        // Arrange
        Long postId = 1L;
        doNothing().when(postDao).deleteById(postId);

        // Act
        boolean result = postService.remove(postId);

        // Assert
        assertTrue(result);
        verify(postDao).deleteById(postId);
    }

    @Test
    void getAll_ShouldReturnListOfPosts() {
        // Arrange
        Post post1 = createTestPost(1L, true);
        Post post2 = createTestPost(2L, false);
        List<Post> posts = List.of(post1, post2);

        PostResponseDto response1 = createTestResponseDto(1L, true);
        PostResponseDto response2 = createTestResponseDto(2L, false);
        List<PostResponseDto> expected = List.of(response1, response2);

        when(postDao.findAll()).thenReturn(posts);
        when(postMapper.toPostResponseList(posts)).thenReturn(expected);

        // Act
        List<PostResponseDto> result = postService.getAll();

        // Assert
        assertEquals(2, result.size());
        assertEquals(expected, result);
    }

    @Test
    void addWithImage_ShouldNotSaveImage_WhenImageIsNull() {
        // Arrange
        PostCreateRequestDto requestDto = createTestRequestDto();
        Post post = createTestPost(null, false);
        Post savedPost = createTestPost(1L, false);
        PostResponseDto expectedResponse = createTestResponseDto(1L, false);

        when(postMapper.fromDto(requestDto)).thenReturn(post);
        when(postDao.save(post)).thenReturn(savedPost);
        when(postMapper.toPostResponse(savedPost)).thenReturn(expectedResponse);

        // Act
        PostResponseDto actualResponse = postService.addWithImage(requestDto, null);

        // Assert
        assertNotNull(actualResponse);
        assertNull(actualResponse.pictureAddress());
        verify(imageStorage, never()).saveImage(any());
    }

    @Test
    void updateWithImage_ShouldKeepExistingImage_WhenNoNewImageProvided() {
        // Arrange
        Long postId = 1L;
        String existingImagePath = "/uploads/existing.jpg";
        PostCreateRequestDto requestDto = createTestRequestDto();
        Post existingPost = createTestPost(postId, true);
        existingPost.setImageAddress(existingImagePath);

        Post updatedPost = createTestPost(postId, true);
        updatedPost.setImageAddress(existingImagePath);

        PostResponseDto expectedResponse = new PostResponseDto(
                postId,
                1L,
                existingImagePath,
                "Test post description",
                testCreationTime
        );

        when(postDao.findById(postId)).thenReturn(Optional.of(existingPost));
        when(postMapper.fromDto(requestDto)).thenReturn(updatedPost);
        when(postDao.save(updatedPost)).thenReturn(updatedPost);
        when(postMapper.toPostResponse(updatedPost)).thenReturn(expectedResponse);

        // Act
        PostResponseDto actualResponse = postService.updateWithImage(requestDto, postId, null);

        // Assert
        assertNotNull(actualResponse);
        assertEquals(existingImagePath, actualResponse.pictureAddress());
        verify(imageStorage, never()).saveImage(any());
    }
}
package ru.shelter.impl;

import org.springframework.stereotype.Service;
import ru.shelter.Interfaces.PostService;
import ru.shelter.dto.request.PostCreateRequestDto;
import ru.shelter.dto.response.PostResponseDto;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class PostImpl implements PostService {

    @Override
    public PostResponseDto add(PostCreateRequestDto Object) {
        return null;
    }

    @Override
    public Optional<PostResponseDto> get(Long aLong) {
        return Optional.empty();
    }

    @Override
    public PostResponseDto update(PostCreateRequestDto Object, Long aLong) {
        return null;
    }

    @Override
    public boolean remove(Long aLong) {
        return false;
    }

    @Override
    public ArrayList<PostResponseDto> getAll() {
        return null;
    }
}

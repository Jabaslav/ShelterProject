package ru.shelter.impl;

import org.springframework.stereotype.Service;
import ru.shelter.Interfaces.PostService;
import ru.shelter.dto.PostDto;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class PostImpl implements PostService {
    @Override
    public PostDto add(PostDto object) {
        return null;
    }

    @Override
    public Optional<PostDto> get(Long id) {
        return Optional.empty();
    }

    @Override
    public boolean update(PostDto Object, Long id) {
        return false;
    }

    @Override
    public boolean remove(Long id) {
        return false;
    }

    @Override
    public ArrayList<PostDto> getAll() {
        return null;
    }
}

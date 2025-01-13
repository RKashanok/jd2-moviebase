package com.jd2.moviebase.repository;

import com.jd2.moviebase.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}

package com.jd2.moviebase.dto;

import java.util.List;

public record ListWrapper <T> (List<T> list, Integer total) {}

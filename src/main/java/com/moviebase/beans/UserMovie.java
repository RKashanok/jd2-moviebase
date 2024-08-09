package com.moviebase.beans;

import javax.xml.crypto.Data;

public class UserMovie {
    private int accountId;
    private int movieId;
    private String status;
    private Data createdAt;
    private Data updatedAt;

    public UserMovie() {
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Data getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Data createdAt) {
        this.createdAt = createdAt;
    }

    public Data getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Data updatedAt) {
        this.updatedAt = updatedAt;
    }
}

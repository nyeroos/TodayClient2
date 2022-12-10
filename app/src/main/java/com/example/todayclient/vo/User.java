package com.example.todayclient.vo;

import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity
public class User {
    @PrimaryKey
    public Integer id;
    public String surname;
    public String name;
}

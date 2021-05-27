package com.sber;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class Combo implements Serializable {
    private Item object;
    private Item subject;
    private Item result;
    private String message;
}

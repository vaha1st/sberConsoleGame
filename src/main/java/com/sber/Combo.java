package com.sber;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Combo {
    private Item object;
    private Item subject;
    private Item result;
    private String message;
}

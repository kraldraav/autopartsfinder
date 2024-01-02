package ru.kraldraav.autopartsfinder.models;

import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
public class Autopart {
    public String Name;
    public String Description;
    public String Price;

    @Override
    public String toString() {
        return "%s %s %s".formatted(Name, Description, Price);
    }
}

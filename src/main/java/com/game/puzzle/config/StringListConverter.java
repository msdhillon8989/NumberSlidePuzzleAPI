package com.game.puzzle.config;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Converter
public class StringListConverter implements AttributeConverter<List<Integer>, String> {

    @Override
    public String convertToDatabaseColumn(List<Integer> list) {
        StringBuilder sb = new StringBuilder();
        for(int element : list)
        {
            sb.append(element).append(",");
        }
        sb.setLength(19);
        return sb.toString();

    }

    @Override
    public List<Integer> convertToEntityAttribute(String joined) {

        ArrayList<String> strings = new ArrayList<>(Arrays.asList(joined.split(",")));
        List<Integer> game = new ArrayList<>();
        for(String num :  strings)
        {
            game.add(Integer.valueOf(num));
        }

        return game;
    }

}
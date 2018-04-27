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
        sb.setLength(18);
        System.out.println("Converted string "+sb.toString());
        return sb.toString();

    }

    @Override
    public List<Integer> convertToEntityAttribute(String joined) {

        ArrayList<String> strings = new ArrayList<>(Arrays.asList(joined.split(",")));
        List<Integer> game = new ArrayList<>();
        for(String num :  strings)
        {
            try {
                game.add(Integer.valueOf(num));
            }
            catch (Exception e)
            {
                System.out.println("Error while parsing ---  ("+num+")");


            }
        }

        return game;
    }

}
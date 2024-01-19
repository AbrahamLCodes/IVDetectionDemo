package com.integralvending.ivdetectiondemo.data;

import com.integralvending.ivdetectiondemo.models.MArticulo;
import com.integralvending.ivdetectiondemo.models.MCharola;

import java.util.ArrayList;

public class MockData {

    private static ArrayList<MCharola> updatedCharolas = new ArrayList<>();

    public static ArrayList<MArticulo> getMockData() {
        ArrayList<MArticulo> result = new ArrayList<>();

        result.add(new MArticulo(1, "Ruffles", "SR", 0));
        result.add(new MArticulo(2, "Doritos", "SD", 0));
        result.add(new MArticulo(3, "Takis", "BT", 0));
        result.add(new MArticulo(4, "Runners", "BR", 0));
        result.add(new MArticulo(5, "Rancheritos", "SRA", 0));
        result.add(new MArticulo(6, "Sabritas", "SS", 0));

        return result;
    }

    public static ArrayList<MCharola> getCharola(){
        if(!updatedCharolas.isEmpty()) return updatedCharolas;

        ArrayList<MCharola> result = new ArrayList<>();

        result.add(new MCharola(1,1,0));
        result.add(new MCharola(2,3,0));
        result.add(new MCharola(3,4,0));
        result.add(new MCharola(4,2,0));
        result.add(new MCharola(5,6,0));

        return result;

    }

    public static void updateCharolas(ArrayList<MCharola> charolas) {
        updatedCharolas = charolas;
    }


}

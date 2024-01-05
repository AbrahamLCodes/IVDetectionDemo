package com.integralvending.ivdetectiondemo.ui.data;

import com.integralvending.ivdetectiondemo.ui.models.MArticulo;

import java.util.ArrayList;

public class MockData {

    public static ArrayList<MArticulo> getMockData() {
        ArrayList<MArticulo> result = new ArrayList<>();

        result.add(new MArticulo(1, "Ruffles", "1", 6));
        result.add(new MArticulo(2, "Doritos", "1", 1));
        result.add(new MArticulo(3, "Takis", "1", 3));
        result.add(new MArticulo(4, "Runners", "1", 1));
        result.add(new MArticulo(5, "Rancheritos", "1", 9));
        result.add(new MArticulo(6, "Sabritas", "1", 13));

        return result;
    }

}

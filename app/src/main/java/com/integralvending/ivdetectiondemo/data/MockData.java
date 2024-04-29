package com.integralvending.ivdetectiondemo.data;

import com.integralvending.ivdetectiondemo.models.MArticulo;
import com.integralvending.ivdetectiondemo.models.MCharola;

import java.util.ArrayList;

public class MockData {

    private static ArrayList<MCharola> updatedCharolas = new ArrayList<>();
    private static ArrayList<MArticulo> updateMockData = new ArrayList<>();

    public static ArrayList<MArticulo> getMockData() {
        if(!updateMockData.isEmpty()) return updateMockData;

        ArrayList<MArticulo> result = new ArrayList<>();

        result.add(new MArticulo(1, "ChettosNaranjas", "SR", 0));
        result.add(new MArticulo(2, "DoritosNegros", "SD", 0));
        result.add(new MArticulo(3, "ChipsMoradas", "BT", 0));
        result.add(new MArticulo(4, "ChipsAmarillas", "BR", 0));
        result.add(new MArticulo(5, "ChettosVerdes", "SRA", 0));
        result.add(new MArticulo(6, "ChettosFlamitHot", "SS", 0));

        // label_map = {1: 'ChettosNaranjas', 2: 'ChipsMoradas', 3:'DoritosNegros'}
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
    public static void updateMockData(ArrayList<MArticulo> articulos) {
        updateMockData = articulos;
    }

}

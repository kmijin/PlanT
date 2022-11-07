package com.example.plant01.home;

public class Plants {
    String plantName, plantImg, plantWaterD, plantSize, plantSun, plantOrigin;
    String plantTem, plantHum, plantLevel, plantKeyWord;
//    ArrayList plantTip;
    int plantWaterC;

    Plants(){}

    public Plants(String plantName, String plantImg, String plantWaterD, String plantSize, String plantSun
            , String plantOrigin, String plantTem, String plantHum, String plantLevel, String plantKeyWord, int plantWaterC) {
        this.plantName = plantName;
        this.plantImg = plantImg;
//        this.plantTip = plantTip;
        this.plantWaterD = plantWaterD;
        this.plantSize = plantSize;
        this.plantSun = plantSun;
        this.plantOrigin = plantOrigin;
        this.plantTem = plantTem;
        this.plantHum = plantHum;
        this.plantLevel = plantLevel;
        this.plantKeyWord = plantKeyWord;
        this.plantWaterC = plantWaterC;
    }

    public String getPlantName() {
        return plantName;
    }

    public void setPlantName(String plantName) {
        this.plantName = plantName;
    }

    public String getPlantImg() {
        return plantImg;
    }

    public void setPlantImg(String plantImg) {
        this.plantImg = plantImg;
    }

//    public ArrayList getPlantTip() {
//        return plantTip;
//    }
//
//    public void setPlantTip(ArrayList plantTip) {
//        this.plantTip = plantTip;
//    }

    public String getPlantWaterD() {
        return plantWaterD;
    }

    public void setPlantWaterD(String plantWaterD) {
        this.plantWaterD = plantWaterD;
    }

    public String getPlantSize() {
        return plantSize;
    }

    public void setPlantSize(String plantSize) {
        this.plantSize = plantSize;
    }

    public String getPlantSun() {
        return plantSun;
    }

    public void setPlantSun(String plantSun) {
        this.plantSun = plantSun;
    }

    public String getPlantOrigin() {
        return plantOrigin;
    }

    public void setPlantOrigin(String plantOrigin) {
        this.plantOrigin = plantOrigin;
    }

    public String getPlantTem() {
        return plantTem;
    }

    public void setPlantTem(String plantTem) {
        this.plantTem = plantTem;
    }

    public String getPlantHum() {
        return plantHum;
    }

    public void setPlantHum(String plantHum) {
        this.plantHum = plantHum;
    }

    public String getPlantLevel() {
        return plantLevel;
    }

    public void setPlantLevel(String plantLevel) {
        this.plantLevel = plantLevel;
    }

    public String getPlantKeyWord() {
        return plantKeyWord;
    }

    public void setPlantKeyWord(String plantKeyWord) {
        this.plantKeyWord = plantKeyWord;
    }

    public int getPlantWaterC() {
        return plantWaterC;
    }

    public void setPlantWaterC(int plantWaterC) {
        this.plantWaterC = plantWaterC;
    }
}

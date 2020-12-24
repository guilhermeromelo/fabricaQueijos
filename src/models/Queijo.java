package models;

public class Queijo {
    private int queijoID;
    private double weight;
    private double pricePerKg;
    private String queijoType;
    private double recommendedTemperature;

    //RETIREI O ID DOS CONSTRUTORES POIS NO BANCO USEI O TIPO SERIAL QUE JÁ FAZ O AUTOINCREMENTO
    public Queijo() {
        this.weight = 0;
        this.pricePerKg = 0;
        this.queijoType = "N/C";
        this.recommendedTemperature = 0;
    }
    
    //RETIREI O ID DOS CONSTRUTORES POIS NO BANCO USEI O TIPO SERIAL QUE JÁ FAZ O AUTOINCREMENTO
    public Queijo(double weight, double pricePerKg, String queijoType, double recommendedTemperature) {
        this.weight = weight;
        this.pricePerKg = pricePerKg;
        this.queijoType = queijoType;
        this.recommendedTemperature = recommendedTemperature;
    }

    public int getQueijoID() {
        return queijoID;
    }

    public void setQueijoID(int queijoID) {
        this.queijoID = queijoID;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getPricePerKg() {
        return pricePerKg;
    }

    public void setPricePerKg(double pricePerKg) {
        this.pricePerKg = pricePerKg;
    }

    public String getQueijoType() {
        return queijoType;
    }

    public void setQueijoType(String queijoType) {
        this.queijoType = queijoType;
    }

    public double getRecommendedTemperature() {
        return recommendedTemperature;
    }

    public void setRecommendedTemperature(double recommendedTemperature) {
        this.recommendedTemperature = recommendedTemperature;
    }

    

    @Override
    public String toString() {
        return "Queijo{" + "id=" + queijoID + ", weight=" + weight + ", pricePerKg=" + pricePerKg + ", queijoType=" + queijoType + ", recommendedTemperature=" + recommendedTemperature + '}';
    }
}

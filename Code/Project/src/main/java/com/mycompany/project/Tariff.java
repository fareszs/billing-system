public class Tariff {

    private static String region;
    private static double pricePerUnit;

    public Tariff(String reg, double price) {
    region = reg;
    pricePerUnit = price;
    }

    public static String getRegion() {
        return region;
    }

    public static void setRegion(String reg) {
        region = reg;
    }

    public static double getPricePerUnit() {
        return pricePerUnit;
    }

    public static void setPricePerUnit(double price) {
        pricePerUnit = price;
    }

    public static String toStr() {
        return "Tariff [region=" + region + ", pricePerUnit=" + pricePerUnit + "]";
    }
}


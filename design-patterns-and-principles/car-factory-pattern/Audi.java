public class Audi extends Car {

    @Override
    public String getBrand() {
        return this.brand;
    }

    @Override
    public boolean is4x4() {
        return is4x4;
    }

    @Override
    public String getModel() {
        return this.model;
    }

    private String brand;
    private String model;
    private boolean is4x4;

    Audi(String model, boolean is4x4){
        this.brand = "Audi";
        this.model = model;
        this.is4x4 = is4x4;
    }
}

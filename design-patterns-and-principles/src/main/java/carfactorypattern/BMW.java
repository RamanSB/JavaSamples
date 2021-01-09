package carfactorypattern;
/**
 * BMW is a Car hence opting for inheritance (IS-A) opposed to implentation via interface (HAS-A)
 */
public class BMW extends Car {

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

    BMW(String model, boolean is4x4){
        this.brand = "BMW";
        this.model = model;
        this.is4x4 = is4x4;
    }
}

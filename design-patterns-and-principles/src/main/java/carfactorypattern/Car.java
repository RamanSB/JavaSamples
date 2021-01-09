package carfactorypattern;
public abstract class Car {

    public abstract String getBrand();

    public abstract boolean is4x4();

    public abstract String getModel();

    @Override
    public String toString(){
        return String.format(getBrand() + " " + getModel());
    }
}
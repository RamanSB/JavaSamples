package carfactorypattern;
public class CarFactory {

    static Car getCar(String carType, String model, boolean is4x4){
        Car car = switch(carType){
            case "BMW" -> new BMW(model, is4x4);
            case "AUDI" -> new Audi(model, is4x4);
            case "MERCEDES" -> new MercedesBenz(model, is4x4);
            default -> throw new IllegalArgumentException();
        };
        return car;
    }


    public static void main(String[] args){
        Car bmw = CarFactory.getCar("BMW", "3-Series", false);
        Car benz = CarFactory.getCar("MERCEDES", "G-Wagon", true);
        System.out.println(bmw);
        System.out.println(benz);
    }
}

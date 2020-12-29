/**
 * Any objects implementing this interface HAS the capability of flying and conducting transport in air, i.e. is an aerial
 * vehicle. This simply allows us to mark objects as being able to fly.
 */
public interface AirTransport{

    void fly();

    void makeSound();

    String getAerialVehicle();
}
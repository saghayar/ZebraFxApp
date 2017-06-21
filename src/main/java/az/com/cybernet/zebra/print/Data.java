
package az.com.cybernet.zebra.print;

/**
 *
 * @author Lenovo
 */
 public class Data{
      private int magnification ; 
      private  int xCoordinat ;
      private  int yCoordinat;
    

    public Data() {
    }

    public Data(int magnification, int xCoordinat, int yCoordinat) {
        this.magnification = magnification;
        this.xCoordinat = xCoordinat;
        this.yCoordinat = yCoordinat;
    }

    public int getMagnification() {
        return magnification;
    }

    public void setMagnification(int magnification) {
        this.magnification = magnification;
    }

    public int getxCoordinat() {
        return xCoordinat;
    }

    public void setxCoordinat(int xCoordinat) {
        this.xCoordinat = xCoordinat;
    }

    public int getyCoordinat() {
        return yCoordinat;
    }

    public void setyCoordinat(int yCoordinat) {
        this.yCoordinat = yCoordinat;
    }
 
      
    
        
   }

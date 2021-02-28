package it.unibo.boundaryWalk;

public class BoundaryWalk {
    private MoveVirtualRobot vr_controller = new MoveVirtualRobot(new HTTPCommunication("localhost", 8090));
    private int step = 500;
    private int shorterStep = 150;

    /*
    * invia i comandi per far eseguire al robot un giro della stanza
    *
    * ritorna sempre 0.
    */
    public int start(){
        // chiama _start(false)
        return _start(false);
    }

    /*
    * The robot will be put in the starting position
    */
    public void resetRobot(){
        vr_controller = new MoveVirtualRobot();
    }

    /*
    * ogni volta che il robot colpisce un muro, eseguirà
    * dei movimenti extra per controllare se rileva degli angoli.
    *
    * ritorna il numero di angoli individuati.
    */
    public int startWithCornerDetection(){
        // chiama _start(true)
        return _start(true);
    }


    private int _start(boolean cornerDetection){
        int corners = 0;

        // per ogni muro della stanza
        for(int i=0; i<4; i++) {
            // continua in avanti finchè non fallisce la mossa
            while (!vr_controller.moveForward(step)) ;

            // controlla se si trova in un angolo
            if (cornerDetection) {
                System.out.println("Applying corner detection");
                if (vr_controller.moveForward(shorterStep)
                        && !vr_controller.moveRight(shorterStep)
                        && vr_controller.moveForward(shorterStep)) {
                    corners++;
                    vr_controller.moveLeft(shorterStep);
                }
            }

            // gira a sinistra e riprende il ciclo
            vr_controller.moveLeft(step);
        }

        return corners;
    }

    public static void main(String[] args){
        System.out.println(new BoundaryWalk().start());
    }
}

package nacnacserver

class Nac {

    User user
    User nacer
    String message
    boolean nac = false

    static constraints = {
        user nullable: false
        nacer nullable: true
        message nullable: true
    }
}

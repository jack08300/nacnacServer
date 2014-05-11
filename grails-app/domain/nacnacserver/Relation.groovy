package nacnacserver

public enum Status {
    REQUESTING, FRIEND, LOVER, BLOCK
}

class Relation {

    User relater
    User related
    Status relationStatus = Status.REQUESTING;
    Date relationDate = new Date()

    static constraints = {
        relater nullable: false
        related nullable: false
        relationStatus nullable: false
    }
}

package nacnacserver

class User {
    String email
    String password
    String name
    Date joinDate = new Date()
    static constraints = {
        email nullable: false
        password nullable: false
        name nullable: true
    }
}

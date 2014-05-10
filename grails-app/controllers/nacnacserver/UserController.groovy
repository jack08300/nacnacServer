package nacnacserver

import grails.converters.JSON

class UserController {
    static scaffold = true

    def loginOrRegister(String email, String password, boolean register){
        def user = User.findByEmail(email)
        Map result
        if(user){
            if(password == user.password){
                result = [success: true, message: ""]
            }else{
                result = [success: false, message: "Wrong password"]
            }
        }else{
            if(register){
                new User(email: email, password: password).save(flush:true, failOnError: true)
                result = [success: true, message: ""]
            }else{
                result = [success: false, register: true, message: "Please Register"]
            }

        }
        println result
        render result as JSON
    }
}

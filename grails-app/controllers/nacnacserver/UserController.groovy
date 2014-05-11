package nacnacserver

import grails.converters.JSON

class UserController {
    static scaffold = true

    def loginOrRegister(String email, String password, String name, boolean register){
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
                new User(email: email, password: password, name: name).save(flush:true, failOnError: true)
                result = [success: true, message: ""]
            }else{
                result = [success: false, register: true, message: "Please Register"]
            }

        }
        println result
        render result as JSON
    }

    def findUserByEmail(String email){
        def user = User.findByEmail(email)
        //Map result
        if(user){
            //result = [email: user.email, name: user.name, success: true, message: '']
        }else{
            //result = [success: true, message: "The user doesn't exists"]
        }

        render user
    }

    def addFriend(String userEmail, String friendEmail){
        println userEmail;
        println friendEmail;
        def user = User.findByEmail(userEmail)
        def friend = User.findByEmail(friendEmail)
        Map result = [success: true]
        if(user == null || friend == null){
            result = [success: false, message: "Can't find the user"]
            render result as JSON
            return
        }
        new Relation(relater: user, related: friend).save(flush: true, failOnError: true)


        render result as JSON
    }
}

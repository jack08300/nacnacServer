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

    def getFriendList(String email){
        User user = User.findByEmail(email)
        def relation = Relation.findByRelaterOrRelated(user, user)
        Map result
        String friendList = ""
        relation.each(){ relate ->
            if(relate.relater == user){
                friendList += relate.related.email +","
            }else{
                friendList += relate.relater.email + ","
            }
        }
        result = [success: true, list: friendList.substring(0, friendList.length()-1)]

        render result as JSON
    }

    def removeNacing(String userEmail, String nacedEmail){
        User nacer = User.findByEmail(userEmail)
        User naced = User.findByEmail(nacedEmail)
        Map result
        if(!nacer || !naced){
            result = [success: false, message: "Can't find the user"]
        }else{
            def nac = Nac.findByUserAndNacer(naced, nacer)
            if(nac){
                nac.nac = false
                nac.message = null
                nac.save(flush: true, failOnError: true)
                result = [success: true, message: "OK, You Done."]
            }else{
                result = [success: false, message: "Can't find Nacing history."]
            }

        }

        render result as JSON


    }

    def nacToSomeone(String userEmail, String nacedEmail, String message){
        User nacer = User.findByEmail(userEmail)
        User naced = User.findByEmail(nacedEmail)
        Map result
        if(!nacer || !naced){
            result = [success: false, message: "Can't find the user"]
        }else{
            def nac = Nac.findByUser(naced) ?: new Nac(user: naced, nacer: nacer)
            nac.nac = true
            nac.message = message
            nac.nacer = nacer
            nac.save(flush: true, failOnError: true)
            result = [success: true, message: "Connected Please keep hold until you don't want to nac anymore"]
            println nac
        }

        render result as JSON

    }

    def nacToYou(String email){
        User user = User.findByEmail(email)
        Map result = [:]
        if(user){
            def nac = Nac.findByUser(user) ?: new Nac(user: user).save(flush: true, failOnError: true)
            result = [success: true, nacing: nac.nac, nacer: nac.nacer.email, message: nac.message]
        }

        render result as JSON
    }

    def addFriend(String userEmail, String friendEmail){
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

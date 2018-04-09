package cz.levinzonr.damiapp.model.entities

class PostObject {

     class Login() {
        var email = ""
        var password = ""

        constructor(_email: String, _password: String) : this () {
            email = _email
            password = _password
        }

    }
}
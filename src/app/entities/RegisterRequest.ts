export class RegisterRequest{
    name: string
    surname: string
    username: string
    password: string

    constructor(name: string = "", surname: string = "", username: string = "", password: string = ""){
        this.name = name
        this.surname = surname
        this.username = username
        this.password = password
    }
}
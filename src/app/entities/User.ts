export class User{
    name: string
    surname: string
    username: string
    password: string
    status: string

    constructor(name: string = "", surname: string = "", username: string = "", password: string = "", status: string = ""){
        this.name = name
        this.surname = surname
        this.username = username
        this.password = password
        this.status = status
    }
}
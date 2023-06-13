import { User } from "./User"

export class Device{
    serialNumber: string
    user: any

    constructor(serialNumber: string, user = new User("", "", "", "", "ACTIVE")){
        this.serialNumber = serialNumber
        this.user = user
    }
}
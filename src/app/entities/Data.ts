export class Data{
    dateTime: Date
    airTemperature: number
    airHumidity: number
    soilMoisture: number

    constructor(dateTime: Date, airTemperature: number, airHumidity: number, soilMoisture: number){
        this.dateTime = dateTime
        this.airTemperature = airTemperature
        this.airHumidity = airHumidity
        this.soilMoisture = soilMoisture
    }
}
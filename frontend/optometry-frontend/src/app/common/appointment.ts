export class Appointment {
  constructor(
    public id: number,
    public startTime: string,
    public endTime: string,
    public patientFirstName: string,
    public patientLastName: string,
    public optometristFirstName: string,
    public optometristLastName: string,
    public status: string,
    public workName: string
  ) {}
}

export class Appointment {
  constructor(
    public id: number,
    public date: string,
    public patientFirstName: string,
    public patientLastName: string,
    public optometristFirstName: string,
    public optometristLastName: string,
    public status: string,
    public workName: string,
    public slot: string
  ) {}
}

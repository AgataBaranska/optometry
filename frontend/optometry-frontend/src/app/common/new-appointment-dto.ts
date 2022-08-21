export class NewAppointmentDTO {
  constructor(
    public date: string | undefined,
    public patientId: number | undefined,
    public optometristId: number | undefined,
    public workName: string | undefined,
    public slot: number | undefined
  ) {}
}

export class Patient {
  constructor(
    public id: number,
    public firstName: string,
    public lastName: string,
    public pesel: string,
    public telephone: string,
    public email: string
  ) {}
}

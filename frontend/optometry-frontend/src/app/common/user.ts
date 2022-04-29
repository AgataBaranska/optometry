export class User {
  id!: number;
  username!: string;
  password!: string;
  firstName!: string;
  lastName!: string;
  pesel!: string;
  email!: string;
  telephone!: string;
  jwtToken?: string;
  constructor() {}
}

import { Role } from './role';

export class User {
  id!: number;
  username!: string;
  password!: string;
  firstName!: string;
  lastName!: string;
  pesel!: string;
  email!: string;
  telephone!: string;
  roles!: Role[];

  street!: string;
  city!: string;
  country!: string;
  zipCode!: string;

  constructor() {}
}

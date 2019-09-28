import {RoleName} from "./roleName";

export class EmployeeProfile {
    id?: number;
  username: string;
    name: string;
    surname: string;
    email: string;
    roleName: RoleName;
 //   password?: string;

  // private Long id;
  // private String username;
  // private String name;
  // private String surname;
  // private String email;
  // private RoleName roleName;

//
// {
//   "id": 20,
//   "username": "monika",
//   "name": "ownrflkjw",
//   "surname": "jmlkjmslk",
//   "email": "jhnwefvk@wp.pl",
//   "roleName": "EMPLOYEE"
// }


  constructor(id: number, username: string, name: string, surname: string, email: string, roleName: RoleName) {
    this.id = id;
    this.username = username;
    this.name = name;
    this.surname = surname;
    this.email = email;
    this.roleName = roleName;
  }
}

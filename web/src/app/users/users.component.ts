import { Component, OnInit } from '@angular/core';
import { User } from '../user'
import { UserService } from '../user.service'
import { LoggedUserService } from '../loggeduser.service'

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {

  user: User = {
    id: 1,
    name: 'Homer Simpson',
    email: 'homer.simpson@gmail.com'
  };

  selectedUser: User;

  users: User[];

  loggedUser: string;

  constructor(private userService: UserService, private loggedUserService: LoggedUserService) { }

  getUsers(): void {
    this.userService.getUsers().subscribe(users => this.users = users);
    this.loggedUserService.getLoggedUser().subscribe(user => this.loggedUser = user);
  }

  ngOnInit(): void {
    this.getUsers();
  }

  onSelect(user: User): void {
    this.selectedUser = user;
  }
}

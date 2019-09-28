import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, AbstractControl } from '@angular/forms';
import { map, first } from 'rxjs/operators';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';

import { AuthService } from '../../app-auth/services/auth.service';
import { AlertService } from '../../app-alert/services/alert.service';
import {RoleService} from "../role.service";

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styles: []
})
export class SignupComponent implements OnInit {

  registerForm: FormGroup;
 message: string;

  constructor(
    private formBuilder: FormBuilder,
    private toastr: ToastrService,
    private router: Router,
    private authService: AuthService,
    private alertService: AlertService, private roleService: RoleService

  ) { }

  ngOnInit() {
    this.registerForm = this.formBuilder.group({
      name: ['', [Validators.required, Validators.minLength(4)]],
      surname: ['', [Validators.required, Validators.minLength(4)]],
      username: [
        '',
        // [Validators.required, Validators.minLength(3), Validators.maxLength(15)],
        // this.validateUsernameAvailability.bind(this)
      ],
      email: [
        '',
        // [Validators.required, Validators.pattern("^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$")],
        // this.validateEmailNotTaken.bind(this)
      ],
      password: ['', [Validators.required, Validators.minLength(6), Validators.maxLength(20)]],
      roleName: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(15)]]
    })

//    this.roleService.currentMessage.subscribe(messaga => this.message=messaga);

  }

//   newMessage(): void{
//     this.roleService.changeMessage(this.registerForm.controls.roleName.value);
// };

  // convenience getter for easy access to form fields
  get f() { return this.registerForm.controls; }

  onFormSubmit() {
      // stop here if form is invalid
      if (this.registerForm.invalid) {
          return;
      }
      this.authService.register(this.registerForm.value)
        .pipe(first())
        .subscribe(
            data => {
                this.toastr.success("Thank you! You're successfully registered. Please Login to continue!");
                this.router.navigate(['/login']);
            },
            error => {
              this.alertService.error(error || "Sorry! Something went wrong. Please try again!");
            });
  }

  validateUsernameAvailability(control: AbstractControl) {
    return this.authService.checkUsernameAvailability(control.value).pipe(map(res => {
      return res.available ? null : { usernameTaken: true };
    }));
  }

  validateEmailNotTaken(control: AbstractControl) {
    return this.authService.checkEmailAvailability(control.value).pipe(map(res => {
      return res.available ? null : { emailTaken: true };
    }));
  }

}

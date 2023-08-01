import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule }   from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
import { AppComponent } from './app.component';
import { MainComponent } from './main/main.component';
import { LoginComponent } from './login/login.component';
import { AuthGuard } from './auth.guard';
import { WriteComponent } from './write/write.component';
import { ViewComponent } from './view/view.component';
import { ProfileComponent } from './profile/profile.component';

@NgModule({
  declarations: [
    AppComponent,
    MainComponent,
    LoginComponent,
    WriteComponent,
    ViewComponent,
    ProfileComponent
  ],
  imports: [
    FormsModule,
    ReactiveFormsModule,
    BrowserModule,
    HttpClientModule,
    RouterModule.forRoot([
    {path: '', component: MainComponent, canActivate:[AuthGuard]},
    {path: 'main', component: MainComponent, canActivate:[AuthGuard]},
    {path: 'write', component: WriteComponent, canActivate:[AuthGuard]},
    {path: 'login', component: LoginComponent},
    {path: 'view', component: ViewComponent},
    {path: 'profile', component: ProfileComponent},
  ]),
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
  
 }

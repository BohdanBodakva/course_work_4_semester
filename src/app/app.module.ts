import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MainPageComponent } from './main-page/main-page.component';
import { ParametersComponent } from './parameters/parameters.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { MainHeaderComponent } from './main-header/main-header.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { HistoryComponent } from './history/history.component';
import { DeletingHistoryComponent } from './deleting-history/deleting-history.component';
import { AverageValuesComponent } from './average-values/average-values.component';
import { SignUpComponent } from './sign-up/sign-up.component';
import { LogInComponent } from './log-in/log-in.component';
import { RouterModule } from '@angular/router';
import { AdminPageComponent } from './admin-page/admin-page.component';
import { UserPageComponent } from './user-page/user-page.component';
import { AddDeviceComponent } from './add-device/add-device.component';


@NgModule({
  declarations: [
    AppComponent,
    MainPageComponent,
    ParametersComponent,
    PageNotFoundComponent,
    MainHeaderComponent,
    HistoryComponent,
    DeletingHistoryComponent,
    AverageValuesComponent,
    SignUpComponent,
    LogInComponent,
    AdminPageComponent,
    UserPageComponent,
    AddDeviceComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    RouterModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }

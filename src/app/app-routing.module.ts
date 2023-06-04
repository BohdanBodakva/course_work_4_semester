import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MainPageComponent } from './main-page/main-page.component';
import { ParametersComponent } from './parameters/parameters.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { HistoryComponent } from './history/history.component';
import { DeletingHistoryComponent } from './deleting-history/deleting-history.component';
import { AverageValuesComponent } from './average-values/average-values.component';
import { SignUpComponent } from './sign-up/sign-up.component';
import { LogInComponent } from './log-in/log-in.component';
import { AdminPageComponent } from './admin-page/admin-page.component';

const routes: Routes = [
  { path: "", component: MainPageComponent },
  { path: "parameters", component: ParametersComponent },
  { path: "history", component: HistoryComponent },
  { path: "deleting-history", component: DeletingHistoryComponent },
  { path: "average-values", component: AverageValuesComponent },
  { path: "sign-up", component: SignUpComponent },
  { path: "log-in", component: LogInComponent },
  { path: "admin", component: AdminPageComponent },
  { path: "**", component: PageNotFoundComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

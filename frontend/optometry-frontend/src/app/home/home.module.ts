import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HomeComponent } from './home/home.component';
import { WeatherComponent } from './weather/weather.component';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  { path: '', component: HomeComponent, pathMatch: 'full' },
];
@NgModule({
  declarations: [HomeComponent, WeatherComponent],
  imports: [CommonModule, RouterModule.forChild(routes)],
})
export class HomeModule {}

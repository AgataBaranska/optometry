import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { StatisticsComponent } from './statistics/statistics.component';

const routes: Routes = [
  { path: '', component: StatisticsComponent, pathMatch: 'full' },
];
@NgModule({
  declarations: [StatisticsComponent],
  imports: [CommonModule, RouterModule.forChild(routes)],
})
export class StatisticsModule {}

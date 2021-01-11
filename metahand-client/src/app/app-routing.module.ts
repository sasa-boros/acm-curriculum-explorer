import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ShellComponent } from './core/components/shell/shell.component';

const routes: Routes = [
  {
    path: '',
    component: ShellComponent,
    canActivate: [],
    children: [
      { path: '', redirectTo: '/sparql', pathMatch: 'full' },
      {
        path: 'sparql',
        canActivate: [],
        loadChildren: () => import('./metahand/metahand.module').then(m => m.MetahandModule)
      }
     ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
